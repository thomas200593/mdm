package com.thomas200593.mdm.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor() : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvents(screenEvents: Events.Screen) = when(screenEvents) {
        Events.Screen.OnOpen -> onOpenEvent()
    }
    private fun onOpenEvent() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {

        }
    }
}