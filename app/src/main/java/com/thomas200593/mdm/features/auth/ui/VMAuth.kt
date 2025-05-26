package com.thomas200593.mdm.features.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.session.SessionManager
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.domain.UCGetScreenData
import com.thomas200593.mdm.features.auth.domain.UCSignIn
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.DTOSignIn
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.DialogState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthTypeState
import com.thomas200593.mdm.features.auth.ui.state.ResultSignInState
import com.thomas200593.mdm.features.auth.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucSignIn: UCSignIn,
    private val sessionManager: SessionManager
) : ViewModel() {
    data class UiState(
        val screenData: ScreenDataState = ScreenDataState.Loading,
        val dialog: DialogState = DialogState.None,
        val resultSignIn: ResultSignInState = ResultSignInState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    var formAuth by mutableStateOf(FormAuthState()) ; private set
    private var debounceJob: Job? = null
    private val debounceDelay: Long = 300L // adjust as needed
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    fun onTopBarEvent(events: Events.TopBar) = when (events) {
        is Events.TopBar.BtnSetting.Clicked -> resetTransientState()
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onFormAuthEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.EmailChanged -> {
            updateForm { it.setValue(email = event.email) }
            debounceValidateField(FormAuthState.Companion.FormField.Email)
        }
        is Events.Content.Form.PasswordChanged -> {
            updateForm { it.setValue(password = event.password) }
            debounceValidateField(FormAuthState.Companion.FormField.Password)
        }
        is Events.Content.Form.BtnSignIn.Clicked -> handleSignIn(event.authType)
        is Events.Content.Form.BtnRecoverAccount.Clicked -> {/*TODO*/}
    }
    fun onSignInCallBackEvent(event: Events.Content.SignInCallback) = when (event) {
        Events.Content.SignInCallback.Success -> resetTransientState()
    }
    private fun resetTransientState() {
        uiState.update { it.copy(dialog = DialogState.None, resultSignIn = ResultSignInState.Idle) }
        formAuth = FormAuthState().validateFields()
    }
    private fun handleOpenScreen() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart { sessionManager.archiveAndCleanUpSession(); resetTransientState()
                uiState.update { it.copy(screenData = ScreenDataState.Loading) } }
            .collect { confCommon -> uiState.update { it.copy(
                screenData = ScreenDataState.Loaded(
                    confCommon = confCommon
                ))
            } ; formAuth = revalidateAllFields(formAuth) }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        uiState.update { it.copy(dialog = transform(it.dialog)) }
    private fun updateForm(transform: (FormAuthState) -> FormAuthState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            val updated = transform(formAuth)
            formAuth.takeIf { it != updated }?.let { formAuth = updated }
        }
    private fun debounceValidateField(field : FormAuthState.Companion.FormField) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch { delay(debounceDelay)
            updateForm { it.validateField(field).validateFields() } }
    }
    private fun revalidateAllFields(current: FormAuthState) : FormAuthState =
        current.validateField(FormAuthState.Companion.FormField.Email)
        .validateField(FormAuthState.Companion.FormField.Password).validateFields()
    private fun handleSignIn(authType: FormAuthTypeState) = when (authType) {
        is FormAuthTypeState.LocalEmailPassword -> {
            viewModelScope.launch {
                uiState.value.screenData as? ScreenDataState.Loaded ?: return@launch
                if(uiState.value.resultSignIn == ResultSignInState.Loading) return@launch
                val frozenForm = formAuth.disableInputs()
                val dto = DTOSignIn(
                    email = frozenForm.fldEmail.trim(),
                    authType = AuthType.LocalEmailPassword(password = frozenForm.fldPassword.trim()),
                    timestamp = Constants.NOW_EPOCH_SECOND
                )
                formAuth = frozenForm
                uiState.update { it.copy(resultSignIn = ResultSignInState.Loading, dialog = DialogState.LoadingAuthDialog) }
                ucSignIn.invoke(dto).fold(
                    onSuccess = { createSession(it.first.uid, (dto.timestamp + Constants.WEEK_IN_SECOND)) },
                    onFailure = { err ->
                        val error = err as? Error ?: Error.UnknownError(message = err.message, cause = err.cause)
                        uiState.update {
                            it.copy(resultSignIn = ResultSignInState.Failure(error), dialog = DialogState.None)
                        }
                        formAuth = FormAuthState().validateFields()
                        return@launch
                    }
                )
            }
        }
    }
    private fun createSession(userId: String, expiresAt: Long) {
        viewModelScope.launch {
            uiState.update { it.copy(dialog = DialogState.LoadingSessionDialog) }
            sessionManager.archiveAndCleanUpSession()
            sessionManager.startSession(SessionEntity(userId = userId, expiresAt = expiresAt)).fold(
                onFailure = { err ->
                    val error = err as? Error ?: Error.UnknownError(message = err.message, cause = err.cause)
                    uiState.update {
                        it.copy(resultSignIn = ResultSignInState.Failure(error), dialog = DialogState.None)
                    }
                },
                onSuccess = {
                    uiState.update { it.copy(resultSignIn = ResultSignInState.Success, dialog = DialogState.None) }
                }
            )
        }
    }
}