package com.thomas200593.mdm.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.initial.domain.UCGetDataInitial
import com.thomas200593.mdm.features.initial.entity.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetDataInitial: UCGetDataInitial
) : ViewModel() {
    sealed interface Ui {
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Success(val data: Initial) : DataState
            data class Error(val throwable: Throwable) : DataState
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

    private fun onOpenEvent() = viewModelScope.launch {
        ucGetDataInitial.invoke()
            .catch { t -> uiState.update { it.copy(dataState = Ui.DataState.Error(throwable = t)) } }
            .collect { data -> uiState.update { it.copy(dataState = Ui.DataState.Success(Initial(confCommon = data))) } }
    }
}