package com.thomas200593.mdm.core.design_system.session.entity

sealed interface SessionEvent {
    data object Loading : SessionEvent
    data object Invalid : SessionEvent
    data object NoRole : SessionEvent
    data object Valid : SessionEvent
}