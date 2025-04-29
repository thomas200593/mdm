package com.thomas200593.mdm.features.role_selection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.session.entity.DTOSessionUserData
import com.thomas200593.mdm.core.design_system.util.update
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor() : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen, data: DTOSessionUserData?, error: Throwable?) = when (event) {
        is Events.Screen.Session.Loading -> handleSessionLoading()
        is Events.Screen.Session.Invalid -> handleSessionInvalid(error)
        is Events.Screen.Session.Valid -> handleSessionValid(data)
    }
    private inline fun updateUiState(crossinline transform: (ComponentsState.Loaded) -> ComponentsState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            uiState.update { current ->
                (current.componentsState as? ComponentsState.Loaded)
                    ?. let(transform)
                    ?. let { updatedState -> current.copy(componentsState = updatedState) }
                    ?: current
            }
        }
    private fun handleSessionLoading() = viewModelScope.launch {}
    private fun handleSessionInvalid(error: Throwable?) = viewModelScope.launch {}
    private fun handleSessionValid(data: DTOSessionUserData?) = viewModelScope.launch {}
}