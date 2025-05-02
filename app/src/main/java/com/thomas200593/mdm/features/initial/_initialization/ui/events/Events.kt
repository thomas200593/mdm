package com.thomas200593.mdm.features.initial._initialization.ui.events

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
    sealed interface Content : Events {
        sealed interface Form : Content {
            data class FirstNameChanged(val firstName: String) : Form
            data class LastNameChanged(val lastName: String) : Form
            data class EmailChanged(val email: String) : Form
            data class PasswordChanged(val password: String) : Form
        }
    }
    sealed interface BottomBar : Events {
        sealed interface BtnProceedInit : BottomBar {
            data object Clicked : BtnProceedInit
        }
    }
    sealed interface Dialog : Events {
        data object SuccessDismissed : Dialog
        data object ErrorDismissed : Dialog
    }
}