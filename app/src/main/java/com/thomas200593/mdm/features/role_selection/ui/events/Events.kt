package com.thomas200593.mdm.features.role_selection.ui.events

sealed interface Events {
    sealed interface Screen : Events {
        sealed interface Session : Screen {
            data object Loading : Session
            data object Invalid : Session
            data object Valid : Session
        }
    }
}