package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VMInitialization @Inject constructor() : ViewModel() {
    sealed interface Ui {
        data class FormState(
            val email: String = "",
            val emailError: MutableList<UiText>? = null
        ) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data object Success : DataState
        }
        sealed interface Events : Ui {
            data object OnOpenEvent : Events
            sealed interface Form : Events {
                data class EmailValueChanged(val email: String) : Form
            }
        }
    }
    private val _emailValidator = TxtFieldEmailValidation()
}