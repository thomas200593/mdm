package com.thomas200593.mdm.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.initial.domain.UCGetDataInitial
import com.thomas200593.mdm.features.initial.entity.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetDataInitial: UCGetDataInitial
) : ViewModel() {
    object Ui {
        data class Data(val state : State = State.Loading)
        sealed interface State {
            data object Loading : State
            data class Success(val data: Initial) : State
            data class Error(val throwable: Throwable) : State
        }
        sealed interface Events {
            data object OnOpenEvent : Events
        }
    }
    var state = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events: Ui.Events) {
        when(events) {
            Ui.Events.OnOpenEvent -> onOpenEvent()
        }
    }

    private fun onOpenEvent() = viewModelScope.launch {
        ucGetDataInitial.invoke()
    }
}