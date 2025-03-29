package com.thomas200593.mdm.features.initialization.ui

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import com.thomas200593.mdm.features.auth.entity.AuthProvider
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetDataInitialization: UCGetDataInitialization,
    private val ucCreateDataInitialization: UCCreateInitialUser
) : ViewModel() {
    sealed interface UiComponents {
        data class Form(
            val fldFirstName: TextFieldState = TextFieldState(STR_EMPTY),
            val fldFirstNameEnabled: Boolean = true,
            val fldFirstNameError: List<UiText> = emptyList(),
            val fldLastName: TextFieldState = TextFieldState(STR_EMPTY),
            val fldLastNameEnabled: Boolean = true,
            val fldLastNameError: List<UiText> = emptyList(),
            val fldEmail: TextFieldState = TextFieldState(STR_EMPTY),
            val fldEmailEnabled: Boolean = true,
            val fldEmailError: List<UiText> = emptyList(),
            val fldPassword: TextFieldState = TextFieldState(STR_EMPTY),
            val fldPasswordEnabled: Boolean = true,
            val fldPasswordError: List<UiText> = emptyList(),
            val btnProceedVisible: Boolean = false,
            val btnProceedEnabled: Boolean = false
        ) : UiComponents {
            private val firstNameValidator = TxtFieldPersonNameValidation()
            private val lastNameValidator = TxtFieldPersonNameValidation()
            private val emailValidator = TxtFieldEmailValidation()
            private val passwordValidator = TxtFieldPasswordValidation()
            fun validateField(
                firstName: CharSequence = fldFirstName.text, lastName: CharSequence = fldLastName.text,
                email: CharSequence = fldEmail.text, password: CharSequence = fldPassword.text
            ) = copy(
                fldFirstName = TextFieldState(firstName.toString()),
                fldFirstNameError = firstNameValidator.validate(firstName.toString(), required = true).errorMessages,
                fldLastName = TextFieldState(lastName.toString()),
                fldLastNameError = lastNameValidator.validate(lastName.toString(), required = false).errorMessages,
                fldEmail = TextFieldState(email.toString()),
                fldEmailError = emailValidator.validate(email.toString(), required = true).errorMessages,
                fldPassword = TextFieldState(password.toString()),
                fldPasswordError = passwordValidator.validate(password.toString(), required = true).errorMessages
            ).validateFields()
            fun validateFields() = copy(
                btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
                        fldEmailError.isEmpty() && fldPasswordError.isEmpty(),
                btnProceedEnabled = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
                        fldEmailError.isEmpty() && fldPasswordError.isEmpty()
            )
            fun disableInputs() = copy(
                fldFirstNameEnabled = false,
                fldLastNameEnabled = false,
                fldEmailEnabled = false,
                fldPasswordEnabled = false,
                btnProceedEnabled = false
            )
        }
        sealed interface DialogState : UiComponents {
            data object None : DialogState
            data object ScrDescInfo : DialogState
            data class Error(val t: Throwable?) : DialogState
            data object SuccessInitialization : DialogState
        }
        sealed interface ResultInitialization : UiComponents {
            data object Idle : ResultInitialization
            data object Loading : ResultInitialization
            data class Success(val result: DTOInitialization) : ResultInitialization
            data class Error(val t : Throwable) : ResultInitialization
        }
        sealed interface ComponentState : UiComponents {
            data object Loading : ComponentState
            data class Loaded(
                val confCommon: Common,
                val form: Form,
                val dialogState : DialogState,
                val resultInitialization: ResultInitialization
            ) : ComponentState
        }
        sealed interface Events : UiComponents {
            data object OnOpenEvent : Events
            sealed interface TopAppBarEvents : Events {
                sealed interface BtnScrDescEvents : TopAppBarEvents{
                    data object OnClick : BtnScrDescEvents
                    data object OnDismiss : BtnScrDescEvents
                }
            }
            sealed interface FormEvents : Events {
                data class FldFirstNameValChanged(val firstName: CharSequence) : FormEvents
                data class FldLastNameValChanged(val lastName: CharSequence) : FormEvents
                data class FldEmailValChanged(val email: CharSequence) : FormEvents
                data class FldPasswordValChanged(val password: CharSequence) : FormEvents
                data object BtnProceedOnClick : FormEvents
                data object DialogSuccessInitializationOnClick : FormEvents
                data object DialogErrorInitializationOnClick: FormEvents
            }
        }
    }
    data class UiState(val componentState: UiComponents.ComponentState = UiComponents.ComponentState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onEvent(events: UiComponents.Events) {
        when(events) {
            is UiComponents.Events.OnOpenEvent -> onOpenEvent()
            is UiComponents.Events.TopAppBarEvents.BtnScrDescEvents.OnClick -> updateDialog { UiComponents.DialogState.ScrDescInfo }
            is UiComponents.Events.TopAppBarEvents.BtnScrDescEvents.OnDismiss -> updateDialog { UiComponents.DialogState.None }
            is UiComponents.Events.FormEvents.FldFirstNameValChanged -> updateForm { it.validateField(firstName = events.firstName).validateFields() }
            is UiComponents.Events.FormEvents.FldLastNameValChanged -> updateForm { it.validateField(lastName = events.lastName).validateFields() }
            is UiComponents.Events.FormEvents.FldEmailValChanged -> updateForm { it.validateField(email = events.email).validateFields() }
            is UiComponents.Events.FormEvents.FldPasswordValChanged -> updateForm { it.validateField(password = events.password).validateFields() }
            is UiComponents.Events.FormEvents.BtnProceedOnClick -> onProceedInitialization()
            is UiComponents.Events.FormEvents.DialogSuccessInitializationOnClick -> resetForm()
            is UiComponents.Events.FormEvents.DialogErrorInitializationOnClick -> resetForm()
        }
    }
    private inline fun updateUiState(crossinline transform: (UiComponents.ComponentState.Loaded) -> UiComponents.ComponentState) {
        uiState.update { currentState ->
            (currentState.componentState as? UiComponents.ComponentState.Loaded)
                ?.let(transform)
                ?.let { updatedState -> currentState.copy(componentState = updatedState) }
                ?: currentState
        }
    }
    private fun onOpenEvent() {
        uiState.update { it.copy(UiComponents.ComponentState.Loading) }
        viewModelScope.launch {
            ucGetDataInitialization.invoke().flowOn(ioDispatcher).collectLatest { confCommon ->
                uiState.update { currentState ->
                    currentState.copy(
                        componentState = UiComponents.ComponentState.Loaded(
                            confCommon = confCommon,
                            form = UiComponents.Form(),
                            dialogState = UiComponents.DialogState.None,
                            resultInitialization = UiComponents.ResultInitialization.Idle
                        )
                    )
                }
            }
        }
    }
    private fun updateDialog(transform: (UiComponents.DialogState) -> UiComponents.DialogState) = updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun updateForm(transform: (UiComponents.Form) -> UiComponents.Form) = updateUiState { it.copy(form = transform(it.form)) }
    private fun resetForm() {
        updateUiState {
            it.copy(
                dialogState = UiComponents.DialogState.None,
                resultInitialization = UiComponents.ResultInitialization.Idle,
                form = UiComponents.Form()
            )
        }
    }
    private fun onProceedInitialization() {
        uiState.update { currentState ->
            (currentState.componentState as? UiComponents.ComponentState.Loaded) ?.let { componentState ->
                currentState.copy(
                    componentState = componentState.copy(
                        resultInitialization = UiComponents.ResultInitialization.Loading,
                        form = componentState.form.disableInputs()
                    )
                )
            } ?: currentState
        }
        viewModelScope.launch(ioDispatcher) {
            val form = (uiState.value.componentState as? UiComponents.ComponentState.Loaded)?. form ?: return@launch
            ucCreateDataInitialization.invoke(
                dto = DTOInitialization(
                    firstName = form.fldFirstName.toString(),
                    lastName = form.fldLastName.toString(),
                    email = form.fldEmail.toString(),
                    authType = AuthType.LocalEmailPassword(
                        provider = AuthProvider.LOCAL_EMAIL_PASSWORD,
                        password = form.fldPassword.toString()
                    )
                )
            ).fold(
                onSuccess = { result ->
                    updateUiState {
                        it.copy(
                            resultInitialization = UiComponents.ResultInitialization.Success(result),
                            dialogState = UiComponents.DialogState.SuccessInitialization
                        )
                    }
                },
                onFailure = { err ->
                    updateUiState {
                        it.copy(
                            resultInitialization = UiComponents.ResultInitialization.Error(err),
                            dialogState = UiComponents.DialogState.Error(err)
                        )
                    }
                }
            )
        }
    }
}