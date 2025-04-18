package com.thomas200593.mdm.features.auth.ui.events

import com.thomas200593.mdm.features.auth.ui.state.FormAuthTypeState

sealed interface Events {
    sealed interface Screen : Events {
        data object Opened : Screen
    }
    sealed interface TopBar : Events {
        sealed interface BtnSetting : TopBar {
            data object Clicked : BtnSetting
        }
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
    }
    sealed interface Content : Events {
        sealed interface Form : Content {
            data class EmailChanged(val email: String) : Form
            data class PasswordChanged(val password: String) : Form
            sealed interface BtnSignIn : Form {
                data class Clicked(val authType: FormAuthTypeState) : BtnSignIn
            }
            sealed interface BtnRecoverAccount : Form {
                data object Clicked : BtnRecoverAccount
            }
        }
    }
}