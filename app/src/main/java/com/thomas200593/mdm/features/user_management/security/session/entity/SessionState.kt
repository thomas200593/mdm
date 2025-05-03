package com.thomas200593.mdm.features.user_management.security.session.entity

sealed interface SessionState {
    data object Loading : SessionState
    data class Valid(val data: DTOSessionUserData) : SessionState
    data class Invalid(val t: Throwable) : SessionState
}