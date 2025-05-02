package com.thomas200593.mdm.features.security.auth.ui.state

sealed interface FormAuthTypeState {
    data object LocalEmailPassword : FormAuthTypeState
}