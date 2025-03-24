package com.thomas200593.mdm.features.initialization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor() : ViewModel() {
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
    )
    data class ScrData(val confCommon: Common, val form : Form)
    sealed interface ScrDataState {
        data object Loading : ScrDataState
        data class Loaded(val scrData : ScrData) : ScrDataState
    }
    sealed interface DialogState {
        data object None : DialogState
        data object ScrDescInfo : DialogState
        data class Error(val t: Throwable?) : DialogState
        data object SuccessInitialization : DialogState
    }
    sealed interface ResultInitialization {
        data object Idle : ResultInitialization
        data object Loading : ResultInitialization
        data class Success(val result: DTOInitialization) : ResultInitialization
        data class Error(val t : Throwable) : ResultInitialization
    }
    data class UiState(
        val scrDataState : ScrDataState = ScrDataState.Loading,
        val dialogState : DialogState = DialogState.None,
        val resultInitialization : ResultInitialization = ResultInitialization.Idle
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
            data object DialogSuccessInitializationOnClick : FormEvents
            data object DialogErrorInitializationOnClick: FormEvents
        }
    }
    var uiState = MutableStateFlow(UiState())
        private set
    fun onEvent(events: Events) {
        when(events) {
            is Events.OnOpenEvent -> onOpenEvent()
            is Events.TopAppBarEvents.BtnScrDescEvents.OnClick -> {}
            is Events.TopAppBarEvents.BtnScrDescEvents.OnDismiss -> {}
            is Events.FormEvents.FldFirstNameValChanged -> {}
            is Events.FormEvents.FldLastNameValChanged -> {}
            is Events.FormEvents.FldEmailValChanged -> {}
            is Events.FormEvents.FldPasswordValChanged -> {}
            is Events.FormEvents.BtnProceedOnClick -> onProceedInitialization()
            is Events.FormEvents.DialogSuccessInitializationOnClick -> {}
            is Events.FormEvents.DialogErrorInitializationOnClick -> {}
        }
    }
    private fun onOpenEvent() = viewModelScope.launch {}
    private fun onProceedInitialization() {}
}