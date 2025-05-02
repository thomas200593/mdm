package com.thomas200593.mdm.features.auth.ui.state

sealed interface FormAuthTypeState {
    data object LocalEmailPassword : FormAuthTypeState
}