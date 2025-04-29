package com.thomas200593.mdm.features.role_selection.ui.events

sealed interface Events {
    sealed interface Screen : Events {
        data object Opened : Screen
    }
    sealed interface TopBar : Events {
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
    }
}