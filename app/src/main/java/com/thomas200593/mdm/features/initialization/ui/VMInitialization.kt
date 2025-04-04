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

/**
 * ViewModel responsible for handling initialization-related UI events and state management.
 *
 * This ViewModel processes UI events from different components (screen, top app bar, form, etc.),
 * manages UI state updates, and triggers initialization logic.
 *
 * @param ucGetDataInitialization Use case for retrieving initialization data.
 * @param ucCreateDataInitialization Use case for creating initialization data.
 */
@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization,
    private val ucCreateDataInitialization: UCCreateInitialUser
) : ViewModel() {
    /**
     * Represents the UI state, containing the current [ComponentsState].
     *
     * @property componentsState The state of the UI components, defaulting to [ComponentsState.Loading].
     */
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    /** Holds the current UI state as a [MutableStateFlow]. */
    var uiState = MutableStateFlow(UiState())
        private set
    /**
     * Handles screen-related events.
     *
     * @param screenEvents The screen event to process.
     */
    fun onScreenEvents(screenEvents: Events.Screen) = when(screenEvents) {
        Events.Screen.OnOpen -> onOpenEvent()
    }
    /**
     * Handles TopAppBar-related events.
     *
     * @param topAppBarEvents The TopAppBar event to process.
     */
    fun onTopAppBarEvents(topAppBarEvents: Events.TopAppBar) = when(topAppBarEvents) {
        Events.TopAppBar.BtnScrDesc.OnClick -> updateDialog { DialogState.InfoScrDesc }
        Events.TopAppBar.BtnScrDesc.OnDismiss -> updateDialog { DialogState.None }
    }
    /**
     * Handles dialog-related events.
     *
     * @param dialogEvents The dialog event to process.
     */
    fun onDialogEvents(dialogEvents: Events.Dialog) = when(dialogEvents) {
        Events.Dialog.InitializationErrorOnDismiss -> resetFormAndUiState()
        Events.Dialog.InitializationSuccessOnDismiss -> resetFormAndUiState()
    }
    /**
     * Handles form-related events.
     *
     * @param formEvents The form event to process.
     */
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
    /**
     * Handles BottomAppBar-related events.
     *
     * @param bottomBarEvents The BottomAppBar event to process.
     */
    fun onBottomBarEvents(bottomBarEvents: Events.BottomAppBar) = when(bottomBarEvents) {
        Events.BottomAppBar.BtnProceedInit.OnClick -> onProceedInit()
    }
    /**
     * Handles the event triggered when the screen opens.
     */
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
    /**
     * Updates the UI state based on the provided transformation.
     *
     * @param transform A lambda function that modifies [ComponentsState.Loaded].
     */
    private inline fun updateUiState(crossinline transform: (ComponentsState.Loaded) -> ComponentsState) =
        uiState.update { currentState ->
            (currentState.componentsState as? ComponentsState.Loaded)
                ?. let(transform)
                ?. let{ updatedState -> currentState.copy(componentsState = updatedState)}
                ?: currentState
        }
    /**
     * Updates the dialog state.
     *
     * @param transform A function to transform the current [DialogState].
     */
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    /**
     * Updates the form state.
     *
     * @param transform A function to transform the current [FormState].
     */
    private fun updateForm(transform: (FormState) -> FormState) =
        updateUiState { it.copy(formState = transform(it.formState)) }
    /**
     * Resets the form state and UI state to their initial values.
     */
    private fun resetFormAndUiState() =
        updateUiState { it.copy(dialogState = DialogState.None, resultInitializationState = ResultInitializationState.Idle, formState = FormState()) }
    /**
     * Handles the "Proceed" button click event, triggering the initialization process.
     */
    private fun onProceedInit() = viewModelScope.launch{
        val success = runCatching { updateUiState { componentState -> componentState.copy(resultInitializationState = ResultInitializationState.Loading, formState = componentState.formState.disableInputs()) } }.isSuccess
        if (!success) return@launch
        val form = (uiState.value.componentsState as? ComponentsState.Loaded)?.formState ?: return@launch
        ucCreateDataInitialization.invoke(
            dto = DTOInitialization(
                firstName = form.fldFirstName.text.toString(),
                lastName = form.fldLastName.text.toString(),
                email = form.fldEmail.text.toString(),
                authType = AuthType.LocalEmailPassword(
                    provider = AuthProvider.LOCAL_EMAIL_PASSWORD, password = form.fldPassword.text.toString()
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