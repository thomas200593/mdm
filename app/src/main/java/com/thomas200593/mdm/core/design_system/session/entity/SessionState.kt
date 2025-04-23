package com.thomas200593.mdm.core.design_system.session.entity

import com.thomas200593.mdm.features.user.entity.UserEntity

sealed interface SessionState {
    data object Loading : SessionState
    data class Valid(val session: Pair<SessionEntity, UserEntity>) : SessionState
    data class Invalid(val t: Throwable) : SessionState
}