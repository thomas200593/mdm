package com.thomas200593.mdm.features.auth.ui.events

sealed interface Events {
    sealed interface Screen : Events {
        data object Opened : Screen
    }
}