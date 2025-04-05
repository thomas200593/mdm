package com.thomas200593.mdm.features.initialization.ui.events

sealed interface Events {
    sealed interface Screen : Events {
        data object OnOpen : Screen
    }
    sealed interface TopAppBar : Events {
        sealed interface BtnScrDesc : TopAppBar {
            data object OnClick : BtnScrDesc
            data object OnDismiss : BtnScrDesc
        }
    }
    sealed interface Content : Events {
        sealed interface Form : Content {
            data class FldValChgFirstName(val firstName: CharSequence) : Form
            data class FldValChgLastName(val lastName: CharSequence) : Form
            data class FldValChgEmail(val email: CharSequence) : Form
            data class FldValChgPassword(val password: CharSequence) : Form
        }
    }
    sealed interface BottomAppBar : Events {
        sealed interface BtnProceedInit : BottomAppBar {
            data object OnClick : BtnProceedInit
        }
    }
    sealed interface Dialog : Events {
        data object InitializationSuccessOnDismiss : Dialog
        data object InitializationErrorOnDismiss : Dialog
    }
}