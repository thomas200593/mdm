package com.thomas200593.mdm.features.user_role.ui.events

import com.thomas200593.mdm.features.session.entity.DTOSessionUserData
import com.thomas200593.mdm.features.session.entity.SessionEvent

sealed interface Events {
    sealed interface Screen : Events {
        data object Opened : Screen
    }
    sealed interface Session : Events {
        data class Loading(val ev: SessionEvent.Loading) : Session
        data class Invalid(val ev: SessionEvent.Invalid, val t: Throwable) : Session
        data class NoCurrentRole(val ev: SessionEvent, val data: DTOSessionUserData) : Session
        data class Valid(val ev: SessionEvent, val data: DTOSessionUserData) : Session
    }
    sealed interface Dialog : Events {
        data object ErrorDismissed : Dialog
    }
    sealed interface TopBar : Events {
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
    }
}