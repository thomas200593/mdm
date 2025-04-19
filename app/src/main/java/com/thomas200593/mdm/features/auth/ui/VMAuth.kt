package com.thomas200593.mdm.features.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.domain.UCSignIn
import com.thomas200593.mdm.features.auth.domain.UCGetScreenData
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucSignIn: UCSignIn
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
        is Events.TopBar.BtnSetting.Clicked -> {}
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onFormAuthEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.EmailChanged -> updateForm { it.validateField(email = event.email).validateFields() }
        is Events.Content.Form.PasswordChanged -> updateForm { it.validateField(password = event.password).validateFields() }
        is Events.Content.Form.BtnSignIn.Clicked -> handleSignIn(event.authType)
        is Events.Content.Form.BtnRecoverAccount.Clicked -> {}
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
        /*TODO: call UC Destroy Active Session on onStart? or separate since it depends on result?*/
        ucGetScreenData.invoke()
            .onStart { uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
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
    private fun updateForm(transform: (FormAuthState) -> FormAuthState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            (uiState.value.componentsState as? ComponentsState.Loaded)?.let {
                val updated = transform(formAuth)
                if (updated != formAuth) formAuth = updated
            }
        }
    private fun handleSignIn(authType: FormAuthTypeState) = when (authType) {
        is FormAuthTypeState.LocalEmailPassword -> {
            val frozenForm = formAuth.disableInputs(); formAuth = frozenForm
            updateUiState { componentState -> componentState.copy(dialogState = DialogState.LoadingDialog, resultSignIn = ResultSignIn.Loading) }
            viewModelScope.launch {
                val isLoaded = uiState.value.componentsState is ComponentsState.Loaded
                if (!isLoaded) return@launch
                val dto = DTOSignIn(
                    email = frozenForm.fldEmail,
                    authType = AuthType.LocalEmailPassword(password = frozenForm.fldPassword),
                    timestamp = Instant.now().epochSecond
                )
                val result = ucSignIn.invoke(dto)
                println("User Result : $result")
            }
        }
    }
}