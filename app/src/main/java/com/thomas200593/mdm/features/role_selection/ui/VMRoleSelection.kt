package com.thomas200593.mdm.features.role_selection.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.features.role_selection.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor() : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
}