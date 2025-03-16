package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization,
    private val repoConfLanguage: RepoConfLanguage
) : ViewModel() {
    data class Form(
        val fldFirstName: CharSequence = STR_EMPTY,
        val fldFirstNameError: List<UiText> = emptyList(),
        val fldLastName: CharSequence = STR_EMPTY,
        val fldLastNameError: List<UiText> = emptyList(),
        val fldEmail: CharSequence = STR_EMPTY,
        val fldEmailError: List<UiText> = emptyList(),
        val fldPassword: CharSequence = STR_EMPTY,
        val fldPasswordError: List<UiText> = emptyList(),
        val chbTncChecked: Boolean = false,
        val btnProceedVisible: Boolean = false
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
            val emailValid = emailValidator.validate(fldEmail.toString(), required = true, maxLength = 200).isSuccess
            val passwordValid = passwordValidator.validate(fldPassword.toString(), required = true, maxLength = 200).isSuccess
            val isButtonVisible = emailValid && passwordValid
            return copy(btnProceedVisible = isButtonVisible)
        }
    }
    data class ScrData(
        val confCommon: Common,
        val languageList : List<Language>,
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
            sealed interface BtnLanguageEvents : TopAppBarEvents {
                data class OnSelect(val language: Language) : BtnLanguageEvents
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
            is Events.TopAppBarEvents.BtnLanguageEvents.OnSelect -> onBtnLanguageSelect(events.language)
            is Events.TopAppBarEvents.BtnScrDescEvents.OnClick -> {}
            is Events.TopAppBarEvents.BtnScrDescEvents.OnDismiss -> {}
            is Events.FormEvents.FldFirstNameValChanged -> {}
            is Events.FormEvents.FldLastNameValChanged -> {}
            is Events.FormEvents.FldEmailValChanged -> onFldEmailValChanged(events.email)
            is Events.FormEvents.FldPasswordValChanged -> onFldPasswordValChanged(events.password)
            is Events.FormEvents.BtnProceedOnClick -> {}
        }
    }
    private fun onOpenEvent() = viewModelScope.launch {
        ucGetDataInitialization.invoke().collect { scrData ->
            uiState.update { it.copy(scrDataState = ScrDataState.Loaded(scrData = scrData)) } }
    }
    private fun onBtnLanguageSelect(language: Language) =
        viewModelScope.launch { repoConfLanguage.set(language) }
    private fun onFldEmailValChanged(email: CharSequence) {
        uiState.update {
            (it.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val updatedForm = state.scrData.form.validateEmail(email)
                it.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm)))
            } ?: it
        }
    }
    private fun onFldPasswordValChanged(password: CharSequence) {
        uiState.update {
            (it.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val updatedForm = state.scrData.form.validatePassword(password)
                it.copy(scrDataState = state.copy(scrData = state.scrData.copy(form = updatedForm)))
            } ?: it
        }
    }
}