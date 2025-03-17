package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization
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
        private val emailValidator = TxtFieldEmailValidation()
        private val passwordValidator = TxtFieldPasswordValidation()
        fun validateEmail(email: CharSequence): Form {
            val result = emailValidator.validate(email.toString(), required = true, maxLength = 200)
            return copy(fldEmail = email, fldEmailError = result.errorMessages).validateAll()
        }
        fun validatePassword(password: CharSequence): Form {
            val result = passwordValidator.validate(password.toString(), required = true, maxLength = 200)
            return copy(fldPassword = password, fldPasswordError = result.errorMessages).validateAll()
        }
        fun validateAll(): Form {
            val emailResult = emailValidator.validate(fldEmail.toString(), required = true, maxLength = 200)
            val passwordResult = passwordValidator.validate(fldPassword.toString(), required = true, maxLength = 200)
            return copy(
                fldEmailError = emailResult.errorMessages,
                fldPasswordError = passwordResult.errorMessages,
                btnProceedVisible = emailResult.isSuccess && passwordResult.isSuccess
            )
        }
    }
    data class ScrData(
        val confCommon: Common,
        val form : Form
    )
    sealed interface ScrDataState {
        data object Loading : ScrDataState
        data class Loaded(val scrData : ScrData) : ScrDataState
    }
    data class UiState(
        val scrDataState: ScrDataState = ScrDataState.Loading,
        val result: Int = 1 /*TODO later*/
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
            is Events.FormEvents.FldFirstNameValChanged -> {/*TODO*/}
            is Events.FormEvents.FldLastNameValChanged -> {/*TODO*/}
            is Events.FormEvents.FldEmailValChanged -> onFldEmailValChanged(events.email)
            is Events.FormEvents.FldPasswordValChanged -> onFldPasswordValChanged(events.password)
            is Events.FormEvents.BtnProceedOnClick -> onProceedInitialization()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch {
        uiState.update { it.copy(scrDataState = ScrDataState.Loading) }
        ucGetDataInitialization.invoke().collect { confCommon ->
            uiState.update {
                it.copy(
                    scrDataState = ScrDataState.Loaded(
                        scrData = ScrData(confCommon = confCommon, form = Form())
                    )
                )
            }
        }
    }
    private fun onFldEmailValChanged(email: CharSequence) {
        uiState.update { currentState ->
            (currentState.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val updatedForm = state.scrData.form.validateEmail(email)
                if (updatedForm == state.scrData.form) return@update currentState
                currentState.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm)))
            } ?: currentState
        }
    }
    private fun onFldPasswordValChanged(password: CharSequence) {
        uiState.update { currentState ->
            (currentState.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val updatedForm = state.scrData.form.validatePassword(password)
                if (updatedForm == state.scrData.form) return@update currentState
                currentState.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm)))
            } ?: currentState
        }
    }
    private fun onProceedInitialization() {
        uiState.update { currentState ->
            (currentState.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val updatedForm = state.scrData.form.copy(
                    fldEmailEnabled = false,
                    fldPasswordEnabled = false,
                    btnProceedEnabled = false
                )
                currentState.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm)))
            } ?: currentState
        }
        viewModelScope.launch {
            // Perform initialization logic here
        }
    }
}