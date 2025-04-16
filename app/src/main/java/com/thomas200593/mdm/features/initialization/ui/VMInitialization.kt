package com.thomas200593.mdm.features.initialization.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.entity.AuthProvider
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.initialization.domain.UCGetScreenData
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.ui.events.Events
import com.thomas200593.mdm.features.initialization.ui.state.ComponentsState
import com.thomas200593.mdm.features.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.initialization.ui.state.FormState
import com.thomas200593.mdm.features.initialization.ui.state.ResultInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucCreateDataInitialization: UCCreateInitialUser
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    var formState by mutableStateOf(FormState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    fun onDialogEvent(dialogEvents: Events.Dialog) = when(dialogEvents) {
        is Events.Dialog.ErrorDismissed -> resetState()
        is Events.Dialog.SuccessDismissed -> resetState()
    }
    fun onTopBarEvent(event: Events.TopBar) = when(event) {
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onFormEvent(event: Events.Content.Form) = when(event) {
        is Events.Content.Form.FirstNameChanged -> updateForm { it.validateField(firstName = event.firstName).validateFields() }
        is Events.Content.Form.LastNameChanged -> updateForm { it.validateField(lastName = event.lastName).validateFields() }
        is Events.Content.Form.EmailChanged -> updateForm { it.validateField(email = event.email).validateFields() }
        is Events.Content.Form.PasswordChanged -> updateForm { it.validateField(password = event.password).validateFields() }
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when(event) {
        is Events.BottomBar.BtnProceedInit.Clicked -> handleInitialization()
    }
    private fun handleOpenScreen() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {
            ucGetScreenData.invoke().collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon,
                        dialogState = DialogState.None,
                        resultInitialization = ResultInitialization.Idle
                    )
                ) }
            }
        }
        formState = formState.validateField()
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
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun updateForm(transform: (FormState) -> FormState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            (uiState.value.componentsState as? ComponentsState.Loaded)?.let {
                val updated = transform(formState)
                if (updated != formState) formState = updated
            }
        }
    private fun resetState() {
        updateUiState { it.copy(
            dialogState = DialogState.None,
            resultInitialization = ResultInitialization.Idle
        ) }
        formState = FormState().validateField()
    }
    private fun handleInitialization() {
        formState = formState.disableInputs()
        updateUiState { componentState -> componentState.copy(resultInitialization = ResultInitialization.Loading) }
        viewModelScope.launch {
            val form = (uiState.value.componentsState as? ComponentsState.Loaded)?.let { formState } ?: return@launch
            ucCreateDataInitialization.invoke(
                dto = DTOInitialization(
                    firstName = form.fldFirstName.toString(),
                    lastName = form.fldLastName.toString(),
                    email = form.fldEmail.toString(),
                    authType = AuthType.LocalEmailPassword(provider = AuthProvider.LOCAL_EMAIL_PASSWORD, password = form.fldPassword.toString())
                )
            ).fold(
                onSuccess = { result ->
                    updateUiState { it.copy(
                        resultInitialization = ResultInitialization.Success(result),
                        dialogState = DialogState.SuccessDialog
                    ) }
                },
                onFailure = { err ->
                    updateUiState { it.copy(
                        resultInitialization = ResultInitialization.Error(err),
                        dialogState = DialogState.ErrorDialog(err)
                    ) }
                }
            )
        }
    }
}