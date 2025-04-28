package com.thomas200593.mdm.features.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.session.SessionManager
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.domain.UCGetScreenData
import com.thomas200593.mdm.features.auth.domain.UCSignIn
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.DTOSignIn
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.ComponentsState
import com.thomas200593.mdm.features.auth.ui.state.DialogState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthTypeState
import com.thomas200593.mdm.features.auth.ui.state.ResultSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    var formAuth by mutableStateOf(FormAuthState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    fun onTopBarEvent(events: Events.TopBar) = when (events) {
        is Events.TopBar.BtnSetting.Clicked -> {/*TODO*/}
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onFormAuthEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.EmailChanged -> updateForm { it.validateField(email = event.email).validateFields() }
        is Events.Content.Form.PasswordChanged -> updateForm { it.validateField(password = event.password).validateFields() }
        is Events.Content.Form.BtnSignIn.Clicked -> handleSignIn(event.authType)
        is Events.Content.Form.BtnRecoverAccount.Clicked -> {/*TODO*/}
    }
    fun onSignInCallBackEvent(event: Events.Content.SignInCallback) = when (event) {
        Events.Content.SignInCallback.Success -> handleSignInCallbackSuccess()
    }
    private inline fun updateUiState(crossinline transform: (ComponentsState.Loaded) -> ComponentsState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            uiState.update { current ->
                (current.componentsState as? ComponentsState.Loaded)
                    ?. let(transform)
                    ?. let{ updatedState -> current.copy(componentsState = updatedState)}
                    ?: current
            }
        }
    private fun handleOpenScreen() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart { sessionManager.archiveAndCleanUpSession(); uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
            .collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon,
                        dialogState = DialogState.None,
                        resultSignIn = ResultSignIn.Idle
                    )
                ) }
            }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun updateForm(transform: (FormAuthState) -> FormAuthState) = viewModelScope.launch(Dispatchers.Main.immediate) {
        (uiState.value.componentsState as? ComponentsState.Loaded)
            ?.let { val updated = transform(formAuth); if (updated != formAuth) formAuth = updated }
    }
    private fun handleSignIn(authType: FormAuthTypeState) = when (authType) {
        is FormAuthTypeState.LocalEmailPassword -> {
            val frozenForm = formAuth.disableInputs(); formAuth = frozenForm
            updateUiState { componentState -> componentState.copy(dialogState = DialogState.LoadingAuthDialog, resultSignIn = ResultSignIn.Loading) }
            viewModelScope.launch {
                val isLoaded = uiState.value.componentsState is ComponentsState.Loaded
                if (!isLoaded) return@launch
                val dto = DTOSignIn(
                    email = frozenForm.fldEmail, authType = AuthType.LocalEmailPassword(password = frozenForm.fldPassword),
                    timestamp = Constants.NOW_EPOCH_SECOND
                )
                ucSignIn.invoke(dto).fold(
                    onFailure = { err ->
                        updateUiState { it.copy(resultSignIn = ResultSignIn.Error(err), dialogState = DialogState.None) }
                        formAuth = FormAuthState()
                    },
                    onSuccess = { createSession(it.first.uid, (dto.timestamp + Constants.WEEK_IN_SECOND)) }
                )
            }
        }
    }
    private fun createSession(userId: String, expiresAt: Long) {
        updateUiState { componentState -> componentState.copy(dialogState = DialogState.LoadingSessionDialog) }
        viewModelScope.launch {
            sessionManager.archiveAndCleanUpSession()
            sessionManager.startSession(SessionEntity(userId = userId, expiresAt = expiresAt)).fold(
                onFailure = { err -> updateUiState { it.copy(resultSignIn = ResultSignIn.Error(err), dialogState = DialogState.None) } },
                onSuccess = { updateUiState { it.copy(resultSignIn = ResultSignIn.Success, dialogState = DialogState.None) } }
            )
        }
    }
    private fun handleSignInCallbackSuccess() {
        updateUiState { componentState -> componentState.copy(resultSignIn = ResultSignIn.Idle, dialogState = DialogState.None) }
        formAuth = FormAuthState()
    }
}