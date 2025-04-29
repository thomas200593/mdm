package com.thomas200593.mdm.features.role_selection.ui.events

sealed interface Events {
    sealed interface Screen : Events {
        data object OnOpen : Screen
    }
}