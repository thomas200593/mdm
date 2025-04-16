package com.thomas200593.mdm.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.initial.domain.UCGetDataInitial
import com.thomas200593.mdm.features.initial.ui.events.Events
import com.thomas200593.mdm.features.initial.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetDataInitial: UCGetDataInitial
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    private fun handleOpenScreen() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {
            ucGetDataInitial.invoke().collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(confCommon = confCommon)
                ) }
            }
        }
    }
}