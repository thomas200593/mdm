package com.thomas200593.mdm.features.role_selection.ui.state

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val data : Int
    ) : ComponentsState
}