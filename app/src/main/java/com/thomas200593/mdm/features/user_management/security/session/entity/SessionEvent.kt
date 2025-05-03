package com.thomas200593.mdm.features.user_management.security.session.entity

sealed interface SessionEvent {
    data object Loading : SessionEvent
    data object Invalid : SessionEvent
    data object NoCurrentRole : SessionEvent
    data object Valid : SessionEvent
}