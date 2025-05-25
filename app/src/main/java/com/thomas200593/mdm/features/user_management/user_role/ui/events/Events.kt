package com.thomas200593.mdm.features.user_management.user_role.ui.events

import com.thomas200593.mdm.features.user_management.security.session.entity.DTOSessionUserData
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEvent

sealed interface Events {
    sealed interface Session : Events {
        data class Loading(val event : SessionEvent.Loading) : Session
        data class Invalid(val event : SessionEvent.Invalid) : Session
        data class NoCurrentRole(val event : SessionEvent) : Session
        data class Valid(val event : SessionEvent) : Session
    }
    sealed interface TopBar : Events {
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
    }
}