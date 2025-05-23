package com.thomas200593.mdm.features.user_management.security.auth.ui.state

import com.thomas200593.mdm.core.design_system.error.Error

sealed interface ResultSignInState {
    data object Idle : ResultSignInState
    data object Loading : ResultSignInState
    data object Success : ResultSignInState
    data class Failure(val t: Error) : ResultSignInState
}