package com.thomas200593.mdm.features.user_management.security.auth.ui.state

sealed interface FormAuthTypeState {
    data object LocalEmailPassword : FormAuthTypeState
}