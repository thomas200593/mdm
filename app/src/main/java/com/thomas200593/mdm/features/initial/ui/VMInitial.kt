package com.thomas200593.mdm.features.initial.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.features.conf._ui.entity.UI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(

) : ViewModel() {
    object Ui {
        sealed interface State {
            data object Loading: State
            data class Success(val data: Int): State
            data class Error(val throwable: Throwable): State
        }
        data class Data(val state: State = State.Loading)
        sealed interface Events {
            data object OnOpenEvent: Events
        }
    }

    var state = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events: Ui.Events) {
        when(events) {
            Ui.Events.OnOpenEvent -> onOpenEvent()
        }
    }

    private fun onOpenEvent() {}
}