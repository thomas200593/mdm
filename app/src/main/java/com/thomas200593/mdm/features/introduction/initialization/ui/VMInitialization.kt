package com.thomas200593.mdm.features.introduction.initialization.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.introduction.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.introduction.initialization.domain.UCGetScreenData
import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.introduction.initialization.ui.events.Events
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ScreenDataState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.FormInitializationState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ResultInitializationState
import com.thomas200593.mdm.features.user_management.security.auth.entity.AuthType
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
    data class UiState(
        val screenData: ScreenDataState = ScreenDataState.Loading,
        val dialog: DialogState = DialogState.None,
        val resultInitialization: ResultInitializationState = ResultInitializationState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    var formInitialization by mutableStateOf(FormInitializationState()) ; private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    fun onDialogEvent(dialogEvents: Events.Dialog) = when(dialogEvents) {
        is Events.Dialog.ErrorDismissed -> resetTransientState()
        is Events.Dialog.SuccessDismissed -> resetTransientState()
    }
    fun onTopBarEvent(event: Events.TopBar) = when(event) {
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onFormEvent(event: Events.Content.Form) = when(event) {
        is Events.Content.Form.FirstNameChanged ->
            updateForm { it.validateField(firstName = event.firstName).validateFields() }
        is Events.Content.Form.LastNameChanged ->
            updateForm { it.validateField(lastName = event.lastName).validateFields() }
        is Events.Content.Form.DateOfBirthChanged ->
            updateForm { it.validateField(dateOfBirth = event.dateOfBirth).validateFields() }
        is Events.Content.Form.EmailChanged ->
            updateForm { it.validateField(email = event.email).validateFields() }
        is Events.Content.Form.PasswordChanged ->
            updateForm { it.validateField(password = event.password).validateFields() }
        is Events.Content.Form.CheckBoxChanged ->
            updateForm { it.validateField(chbToCChecked = event.checked).validateFields() }
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when(event) {
        is Events.BottomBar.BtnProceedInit.Clicked -> handleInitialization()
    }
    private fun resetTransientState() {
        uiState.update {
            it.copy(
                dialog = DialogState.None,
                resultInitialization = ResultInitializationState.Idle
            )
        }
        formInitialization = FormInitializationState().validateField()
    }
    private fun handleOpenScreen() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart {
                formInitialization = formInitialization.validateField()
                uiState.update { it.copy(screenData = ScreenDataState.Loading) }
            }
            .collect { (confCommon, initialSetOfRoles) ->
                uiState.update {
                    it.copy(
                        screenData = ScreenDataState.Loaded(
                            confCommon = confCommon,
                            initialSetOfRoles = initialSetOfRoles
                        ),
                        dialog = DialogState.None,
                        resultInitialization = ResultInitializationState.Idle
                    )
                }
            }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) = uiState.update {
        it.copy(dialog = transform(it.dialog))
    }
    private fun updateForm(transform: (FormInitializationState) -> FormInitializationState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            val updated = transform(formInitialization)
            formInitialization.takeIf { it != updated }?.let { formInitialization = updated }
        }
    private fun handleInitialization() = viewModelScope.launch {
        val loaded = uiState.value.screenData as? ScreenDataState.Loaded ?: return@launch
        if(uiState.value.resultInitialization == ResultInitializationState.Loading) return@launch
        val frozenForm = formInitialization.disableInputs()
        val dto = DTOInitialization(
            firstName = frozenForm.fldFirstName.toString().trim(),
            lastName = frozenForm.fldLastName.toString().trim(),
            email = frozenForm.fldEmail.toString().trim(),
            authType = AuthType.LocalEmailPassword(
                password = frozenForm.fldPassword.toString().trim()
            ),
            initialSetOfRoles = loaded.initialSetOfRoles
        )
        formInitialization = frozenForm
        uiState.update {
            it.copy(
                resultInitialization = ResultInitializationState.Loading,
                dialog = DialogState.LoadingDialog
            )
        }
        ucCreateDataInitialization.invoke(dto).fold(
            onSuccess = { result ->
                uiState.update {
                    it.copy(
                        resultInitialization = ResultInitializationState.Success(result),
                        dialog = DialogState.SuccessDialog
                    )
                }
                formInitialization = FormInitializationState().validateFields()
                return@launch
            },
            onFailure = { err ->
                val error = err as Error
                error.printStackTrace()
                uiState.update {
                    it.copy(
                        resultInitialization = ResultInitializationState.Failure(error),
                        dialog = DialogState.ErrorDialog(error)
                    )
                }
                formInitialization = FormInitializationState().validateFields()
                return@launch
            }
        )
    }
}