package com.thomas200593.mdm.features.user_management.security.session.entity

import com.thomas200593.mdm.core.design_system.error.Error

sealed interface SessionState {
    data object Loading : SessionState
    data class Valid(val data : DTOSessionUserData) : SessionState
    data class Invalid(val error : Error) : SessionState
}