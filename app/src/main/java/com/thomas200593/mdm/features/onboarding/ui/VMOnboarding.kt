package com.thomas200593.mdm.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor() : ViewModel() {
    sealed interface Ui {
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Success(val data: Int) : DataState
        }
        sealed interface Events : Ui {
            data object OnOpenEvent : Events
        }
    }

    var uiState = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events : Ui.Events) {
        when (events) {
            Ui.Events.OnOpenEvent -> onOpenEvent()
        }
    }

    private fun onOpenEvent() = viewModelScope.launch {/*TODO*/}
}