package com.thomas200593.mdm.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.auth.domain.UCGetScreenData
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val ucGetScreenData: UCGetScreenData
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    private fun handleOpenScreen() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart { uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
            .collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon
                    )
                ) }
            }
    }
}