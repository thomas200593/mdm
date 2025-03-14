package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.features.initialization.domain.UCGetDataInitialization
import com.thomas200593.mdm.features.initialization.entity.InitializationScrData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetDataInitialization: UCGetDataInitialization
) : ViewModel() {
    sealed interface Ui {
        data class FormData(
            val email: CharSequence = STR_EMPTY,
            val emailError: List<UiText> = emptyList<UiText>(),
            val password: CharSequence = STR_EMPTY,
            val passwordError: List<UiText> = emptyList<UiText>(),
            val tncChecked: Boolean = false,
            val btnProceedEnabled: Boolean = false
        ) : Ui
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Loaded(val data: InitializationScrData) : DataState
        }
        sealed interface Events : Ui {
            data object OnOpenEvent : Events
            sealed interface FormEvent : Events {
                data class EmailValueChanged(val email: CharSequence) : FormEvent
                data class PasswordValueChanged(val password: CharSequence) : FormEvent
                data class TnCValueChanged(val checked: Boolean) : FormEvent
            }
        }
    }

    var uiState = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events: Ui.Events) {
        when(events) {
            is Ui.Events.OnOpenEvent -> onOpenEvent()
            is Ui.Events.FormEvent.EmailValueChanged -> onEmailValueChanged(events.email)
            is Ui.Events.FormEvent.PasswordValueChanged -> onPasswordValueChanged(events.password)
            is Ui.Events.FormEvent.TnCValueChanged -> onTnCValueChanged()
        }
    }

    private fun onOpenEvent() = viewModelScope.launch {
        ucGetDataInitialization.invoke().collect { data ->
            uiState.update { it.copy(dataState = Ui.DataState.Loaded(data = data)) }
        }
    }
    private fun onEmailValueChanged(email: CharSequence) {
        uiState.update {
            (it.dataState as? Ui.DataState.Loaded)?.let { state ->
                it.copy(
                    dataState = state.copy(
                        data = state.data.copy(
                            formData = state.data.formData.copy(
                                email = email
                            )
                        )
                    )
                )
            } ?: it
        }
        validateEmailField()
        shouldShowProceedBtn()
    }
    private fun onPasswordValueChanged(password: CharSequence) {
        uiState.update {
            (it.dataState as? Ui.DataState.Loaded)?.let { state ->
                it.copy(
                    dataState = state.copy(
                        data = state.data.copy(
                            formData = state.data.formData.copy(
                                password = password
                            )
                        )
                    )
                )
            } ?: it
        }
        validatePasswordField()
        shouldShowProceedBtn()
    }
    private fun onTnCValueChanged() {}
    private fun shouldShowProceedBtn() {
        val enabled = (validateEmailField() && validatePasswordField())
        uiState.update {
            (it.dataState as? Ui.DataState.Loaded)?.let { state ->
                it.copy(
                    dataState = state.copy(
                        data = state.data.copy(
                            formData = state.data.formData.copy(
                                btnProceedEnabled = enabled
                            )
                        )
                    )
                )
            } ?: it
        }
    }

    // Fields Validator
    private val _emailValidator = TxtFieldEmailValidation()
    private val _passwordValidator = TxtFieldPasswordValidation()
    private fun validateEmailField(): Boolean {
        val result = _emailValidator.validate(
            input = (uiState.value.dataState as? Ui.DataState.Loaded)?.data?.formData?.email.toString(),
            required = true,
            maxLength = 200
        )
        uiState.update {
            (it.dataState as? Ui.DataState.Loaded)?.let { state ->
                it.copy(
                    dataState = state.copy(
                        data = state.data.copy(
                            formData = state.data.formData.copy(
                                emailError = result.errorMessages
                            )
                        )
                    )
                )
            } ?: it
        }
        return result.isSuccess
    }
    private fun validatePasswordField(): Boolean {
        val result = _passwordValidator.validate(
            input = (uiState.value.dataState as? Ui.DataState.Loaded)?.data?.formData?.password.toString(),
            required = true
        )
        uiState.update {
            (it.dataState as? Ui.DataState.Loaded)?.let { state ->
                it.copy(
                    dataState = state.copy(
                        data = state.data.copy(
                            formData = state.data.formData.copy(
                                passwordError = result.errorMessages
                            )
                        )
                    )
                )
            } ?: it
        }
        return result.isSuccess
    }
}