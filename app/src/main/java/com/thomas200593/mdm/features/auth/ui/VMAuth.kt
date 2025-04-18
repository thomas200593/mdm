package com.thomas200593.mdm.features.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.domain.UCGetScreenData
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.ComponentsState
import com.thomas200593.mdm.features.auth.ui.state.DialogState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val ucGetScreenData: UCGetScreenData
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
        is Events.Content.Form.BtnSignIn.Clicked -> handleSignIn()
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
        ucGetScreenData.invoke()
            .onStart { uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
            .collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon,
                        dialogState = DialogState.None
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
    private fun handleSignIn() {

    }
}