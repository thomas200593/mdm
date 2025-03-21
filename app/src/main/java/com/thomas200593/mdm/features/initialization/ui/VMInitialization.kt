package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.features.auth.entity.AuthProvider
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.domain.UCCreateInitialUser
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization,
    private val ucCreateInitialUser : UCCreateInitialUser
) : ViewModel() {
    data class Form(
        val fldFirstName: CharSequence = STR_EMPTY,
        val fldFirstNameEnabled: Boolean = true,
        val fldFirstNameError: List<UiText> = emptyList(),
        val fldLastName: CharSequence = STR_EMPTY,
        val fldLastNameEnabled: Boolean = true,
        val fldLastNameError: List<UiText> = emptyList(),
        val fldEmail: CharSequence = STR_EMPTY,
        val fldEmailEnabled: Boolean = true,
        val fldEmailError: List<UiText> = emptyList(),
        val fldPassword: CharSequence = STR_EMPTY,
        val fldPasswordEnabled: Boolean = true,
        val fldPasswordError: List<UiText> = emptyList(),
        val btnProceedVisible: Boolean = false,
        val btnProceedEnabled: Boolean = true
    ) {
        private val firstNameValidator = TxtFieldPersonNameValidation()
        private val lastNameValidator = TxtFieldPersonNameValidation()
        private val emailValidator = TxtFieldEmailValidation()
        private val passwordValidator = TxtFieldPasswordValidation()
        fun validateFirstName(firstName: CharSequence) =
            copy(fldFirstName = firstName, fldFirstNameError = firstNameValidator.validate(firstName.toString(), required = true).errorMessages).validateAll()
        fun validateLastName(lastName: CharSequence) =
            copy(fldLastName = lastName, fldLastNameError = lastNameValidator.validate(lastName.toString(), required = false).errorMessages).validateAll()
        fun validateEmail(email: CharSequence) =
            copy(fldEmail = email, fldEmailError = emailValidator.validate(email.toString(), required = true).errorMessages).validateAll()
        fun validatePassword(password: CharSequence) =
            copy(fldPassword = password, fldPasswordError = passwordValidator.validate(password.toString(), required = true).errorMessages).validateAll()
        fun validateAll() = copy(
            fldFirstNameError = firstNameValidator.validate(fldFirstName.toString(), required = true).errorMessages,
            fldLastNameError = lastNameValidator.validate(fldLastName.toString(), required = false).errorMessages,
            fldEmailError = emailValidator.validate(fldEmail.toString(), required = true).errorMessages,
            fldPasswordError = passwordValidator.validate(fldPassword.toString(), required = true).errorMessages,
            btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() && fldEmailError.isEmpty() && fldPasswordError.isEmpty()
        )
        fun isAllFieldValid(): Boolean = validateAll().btnProceedVisible
    }
    data class ScrData(
        val confCommon: Common,
        val form : Form
    )
    sealed interface ScrDataState {
        data object Loading : ScrDataState
        data class Loaded(val scrData : ScrData) : ScrDataState
    }
    sealed interface ResultInitialization {
        data object Idle : ResultInitialization
        data object Loading : ResultInitialization
        data class Success(val result: Result<DTOInitialization>) : ResultInitialization
        data class Error(val t : Throwable) : ResultInitialization
    }
    data class UiState(
        val scrDataState: ScrDataState = ScrDataState.Loading,
        val result : ResultInitialization = ResultInitialization.Idle
    )
    sealed interface Events {
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
        }
    }
    var uiState = MutableStateFlow(UiState())
        private set
    fun onEvent(events: Events) {
        when(events) {
            is Events.OnOpenEvent -> onOpenEvent()
            is Events.TopAppBarEvents.BtnScrDescEvents.OnClick -> {/*TODO*/}
            is Events.TopAppBarEvents.BtnScrDescEvents.OnDismiss -> {/*TODO*/}
            is Events.FormEvents.FldFirstNameValChanged -> updateForm { it.validateFirstName(events.firstName) }
            is Events.FormEvents.FldLastNameValChanged -> updateForm { it.validateLastName(events.lastName) }
            is Events.FormEvents.FldEmailValChanged -> updateForm { it.validateEmail(events.email) }
            is Events.FormEvents.FldPasswordValChanged -> updateForm { it.validatePassword(events.password) }
            is Events.FormEvents.BtnProceedOnClick -> onProceedInitialization()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch {
        uiState.update { it.copy(scrDataState = ScrDataState.Loading) }
        ucGetDataInitialization.invoke().collect { confCommon ->
            uiState.update { it
                .copy(scrDataState = ScrDataState.Loaded(scrData = ScrData(confCommon = confCommon, form = Form())))
            }
        }
    }
    private fun updateForm(transform: (Form) -> Form) {
        uiState.value.let { currentState ->
            val state = currentState.scrDataState as? ScrDataState.Loaded ?: return
            val updatedForm = transform(state.scrData.form)
            if (updatedForm == state.scrData.form) return
            uiState.update { it.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm))) }
        }
    }
    private fun onProceedInitialization() {
        uiState.update { currentState ->
            val state = currentState.scrDataState as? ScrDataState.Loaded ?: return@update currentState
            val form = state.scrData.form
            if (!form.isAllFieldValid()) { return@update currentState.copy(result = ResultInitialization.Error(Throwable("Invalid form fields"))) }
            currentState.copy(
                scrDataState = state.copy(
                    scrData = state.scrData.copy(
                        form = form.copy(
                            fldFirstNameEnabled = false, fldLastNameEnabled = false,
                            fldEmailEnabled = false, fldPasswordEnabled = false,
                            btnProceedEnabled = false
                        )
                    )
                ),
                result = ResultInitialization.Loading
            )
        }
        // Launch a coroutine to perform user initialization
        viewModelScope.launch {
            val state = uiState.value.scrDataState as? ScrDataState.Loaded ?: return@launch
            ucCreateInitialUser.invoke(
                dto = DTOInitialization(
                    firstName = state.scrData.form.fldFirstName.toString(),
                    lastName = state.scrData.form.fldLastName.toString(),
                    email = state.scrData.form.fldEmail.toString(),
                    authType = AuthType.LocalEmailPassword(
                        provider = AuthProvider.LOCAL_EMAIL_PASSWORD.name,
                        password = state.scrData.form.fldPassword.toString()
                    )
                )
            ).fold(
                onSuccess = {
                    /*Update State*/

                },
                onFailure = {
                    /*Update State*/
                }
            )
        }
    }
}