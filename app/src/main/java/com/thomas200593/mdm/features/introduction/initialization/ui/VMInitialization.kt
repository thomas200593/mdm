package com.thomas200593.mdm.features.introduction.initialization.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.user_management.security.auth.entity.AuthType
import com.thomas200593.mdm.features.introduction.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.introduction.initialization.domain.UCGetScreenData
import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.introduction.initialization.ui.events.Events
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ComponentsState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.FormInitializationState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ResultInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMInitialization @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucCreateDataInitialization: UCCreateInitialUser
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    var formInitialization by mutableStateOf(FormInitializationState())
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
        is Events.Content.Form.DateOfBirthChanged -> updateForm { it.validateField(dateOfBirth = event.dateOfBirth).validateFields() }
        is Events.Content.Form.EmailChanged -> updateForm { it.validateField(email = event.email).validateFields() }
        is Events.Content.Form.PasswordChanged -> updateForm { it.validateField(password = event.password).validateFields() }
        is Events.Content.Form.CheckBoxChanged -> updateForm { it.validateField(chbToCChecked = event.checked).validateFields() }
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when(event) {
        is Events.BottomBar.BtnProceedInit.Clicked -> handleInitialization()
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
    private fun resetState() {
        updateUiState { it.copy(
            dialogState = DialogState.None,
            resultInitialization = ResultInitialization.Idle
        ) }
        formInitialization = FormInitializationState().validateField()
    }
    private fun handleOpenScreen() = viewModelScope.launch { ucGetScreenData.invoke()
        .onStart {
            formInitialization = formInitialization.validateField()
            uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        }
        .collect { (confCommon, initialSetOfRoles) ->
            uiState.update { currentState -> currentState.copy(
                componentsState = ComponentsState.Loaded(
                    confCommon = confCommon,
                    dialogState = DialogState.None,
                    initialSetOfRoles = initialSetOfRoles,
                    resultInitialization = ResultInitialization.Idle
                )
            ) }
        }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun updateForm(transform: (FormInitializationState) -> FormInitializationState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            val updated = transform(formInitialization)
            formInitialization.takeIf { it != updated }?.let { formInitialization = updated }
        }
    private fun handleInitialization() = viewModelScope.launch {
        // Step 1: Ensure componentsState is loaded
        val componentsState = uiState.value.componentsState as? ComponentsState.Loaded ?: return@launch
        if(componentsState.resultInitialization == ResultInitialization.Loading) return@launch
        // Step 2: Freeze form inputs
        val frozenForm = formInitialization.disableInputs()
        formInitialization = frozenForm
        // Step 3: Show loading state
        updateUiState { it.copy(resultInitialization = ResultInitialization.Loading, dialogState = DialogState.LoadingDialog) }
        // Step 4: Build DTO
        val dto = DTOInitialization(
            firstName = frozenForm.fldFirstName.toString().trim(),
            lastName = frozenForm.fldLastName.toString().trim(),
            email = frozenForm.fldEmail.toString().trim(),
            authType = AuthType.LocalEmailPassword(password = frozenForm.fldPassword.toString().trim()),
            initialSetOfRoles = componentsState.initialSetOfRoles
        )
        // Step 5: Perform the action and handle result
        ucCreateDataInitialization.invoke(dto).fold(
            onSuccess = { result ->
                updateUiState { it.copy(resultInitialization = ResultInitialization.Success(result), dialogState = DialogState.SuccessDialog) }
                formInitialization = FormInitializationState().validateFields()
            },
            onFailure = { err -> err.printStackTrace()
                updateUiState { it.copy(resultInitialization = ResultInitialization.Error(err), dialogState = DialogState.ErrorDialog(err)) }
                formInitialization = FormInitializationState().validateFields()
            }
        )
    }
}