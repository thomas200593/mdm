package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.auth.entity.AuthProvider
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.ui.events.Events
import com.thomas200593.mdm.features.initialization.ui.state.ComponentsState
import com.thomas200593.mdm.features.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.initialization.ui.state.FormState
import com.thomas200593.mdm.features.initialization.ui.state.ResultInitializationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization,
    private val ucCreateDataInitialization: UCCreateInitialUser
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvents(screenEvents: Events.Screen) = when(screenEvents) {
        Events.Screen.OnOpen -> onOpenEvent()
    }
    fun onTopAppBarEvents(topAppBarEvents: Events.TopAppBar) = when(topAppBarEvents) {
        Events.TopAppBar.BtnScrDesc.OnClick -> updateDialog { DialogState.InfoScrDesc }
        Events.TopAppBar.BtnScrDesc.OnDismiss -> updateDialog { DialogState.None }
    }
    fun onDialogEvents(dialogEvents: Events.Dialog) = when(dialogEvents) {
        Events.Dialog.InitializationErrorOnDismiss -> resetFormAndUiState()
        Events.Dialog.InitializationSuccessOnDismiss -> resetFormAndUiState()
    }
    fun onFormEvents(formEvents: Events.Content.Form) = when(formEvents) {
        is Events.Content.Form.FldValChgFirstName ->
            updateForm { it.validateField(firstName = formEvents.firstName).validateFields() }
        is Events.Content.Form.FldValChgLastName ->
            updateForm { it.validateField(lastName = formEvents.lastName).validateFields() }
        is Events.Content.Form.FldValChgEmail ->
            updateForm { it.validateField(email = formEvents.email).validateFields() }
        is Events.Content.Form.FldValChgPassword ->
            updateForm { it.validateField(password = formEvents.password).validateFields() }
    }
    fun onBottomBarEvents(bottomBarEvents: Events.BottomAppBar) = when(bottomBarEvents) {
        Events.BottomAppBar.BtnProceedInit.OnClick -> onProceedInit()
    }
    private fun onOpenEvent() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {
            ucGetDataInitialization.invoke().distinctUntilChanged().collect { confCommon ->
                uiState.update { currentState ->
                    currentState.copy(
                        componentsState = ComponentsState.Loaded(
                            confCommon = confCommon,
                            formState = FormState(),
                            dialogState = DialogState.None,
                            resultInitializationState = ResultInitializationState.Idle
                        )
                    )
                }
            }
        }
    }
    private inline fun updateUiState(crossinline transform: (ComponentsState.Loaded) -> ComponentsState) {
        uiState.update { currentState ->
            (currentState.componentsState as? ComponentsState.Loaded) ?. let(transform)
                ?. let{ updatedState -> currentState.copy(componentsState = updatedState)}
                ?: currentState
        }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) = updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun updateForm(transform: (FormState) -> FormState) = updateUiState { it.copy(formState = transform(it.formState)) }
    private fun resetFormAndUiState() = updateUiState {
        it.copy(
            dialogState = DialogState.None,
            resultInitializationState = ResultInitializationState.Idle, formState = FormState()
        )
    }
    private fun onProceedInit() = viewModelScope.launch{
        val success = runCatching {
            updateUiState { componentState ->
                componentState.copy(
                    resultInitializationState = ResultInitializationState.Loading,
                    formState = componentState.formState.disableInputs()
                )
            }
        }.isSuccess
        if (!success) return@launch
        val form = (uiState.value.componentsState as? ComponentsState.Loaded)?.formState ?: return@launch
        ucCreateDataInitialization.invoke(
            dto = DTOInitialization(
                firstName = form.fldFirstName.text.toString(),
                lastName = form.fldLastName.text.toString(),
                email = form.fldEmail.text.toString(),
                authType = AuthType.LocalEmailPassword(
                    provider = AuthProvider.LOCAL_EMAIL_PASSWORD,
                    password = form.fldPassword.text.toString()
                )
            )
        ).fold(
            onSuccess = { result ->
                updateUiState {
                    it.copy(
                        resultInitializationState = ResultInitializationState.Success(result),
                        dialogState = DialogState.SuccessInitialization
                    )
                }
            },
            onFailure = { err ->
                updateUiState {
                    it.copy(
                        resultInitializationState = ResultInitializationState.Error(err),
                        dialogState = DialogState.Error(err)
                    )
                }
            }
        )
    }
}