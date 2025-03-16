package com.thomas200593.mdm.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initial.domain.UCGetDataInitial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetDataInitial: UCGetDataInitial
) : ViewModel() {
    data class ScrData(
        val confCommon: Common
    )
    sealed interface ScrDataState {
        data object Loading : ScrDataState
        data class Loaded(val scrData: ScrData) : ScrDataState
    }
    data class UiState(
        val scrDataState: ScrDataState = ScrDataState.Loading
    )
    sealed interface Events {
        data object OnOpenEvent : Events
    }
    var uiState = MutableStateFlow(UiState())
        private set
    fun onEvent(events: Events) {
        when(events) {
            is Events.OnOpenEvent -> onOpenEvent()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch { ucGetDataInitial.invoke().collect { initial ->
        uiState.update { state -> state.copy(scrDataState = ScrDataState.Loaded(scrData = ScrData(confCommon = initial.confCommon))) } }
    }
}