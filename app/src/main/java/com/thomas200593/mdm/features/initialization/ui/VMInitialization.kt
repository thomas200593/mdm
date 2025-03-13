package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field._domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.features.initialization.entity.InitializationScrData
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VMInitialization @Inject constructor() : ViewModel() {
    sealed interface Ui {
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Success(val data: InitializationScrData) : DataState
        }
        sealed interface Events : Ui {
            data object OnOpenEvent : Events
            sealed interface FormEvent : Events {
                data class EmailValueChanged(val email: String) : FormEvent
                data class PasswordValueChanged(val password: String) : FormEvent
            }
        }
    }
    private val _emailValidator = TxtFieldEmailValidation()
    private val _passwordValidator = TxtFieldPasswordValidation()
    var uiState = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events: Ui.Events) {
        when(events) {
            is Ui.Events.OnOpenEvent -> {/*TODO*/}
            is Ui.Events.FormEvent.EmailValueChanged -> {/*TODO*/}
            is Ui.Events.FormEvent.PasswordValueChanged -> {/*TODO*/}
        }
    }
}