package com.thomas200593.mdm.features.bootstrap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.bootstrap.domain.UCGetScreenData
import com.thomas200593.mdm.features.bootstrap.ui.events.Events
import com.thomas200593.mdm.features.bootstrap.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMBootstrap @Inject constructor(
    private val ucGetScreenData: UCGetScreenData
) : ViewModel() {
    data class UiState(val screenData: ScreenDataState = ScreenDataState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    private fun handleOpenScreen() {
        uiState.update { it.copy(screenData = ScreenDataState.Loading) }
        viewModelScope.launch {
            ucGetScreenData.invoke().collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    screenData = ScreenDataState.Loaded(confCommon = confCommon)
                ) }
            }
        }
    }
}