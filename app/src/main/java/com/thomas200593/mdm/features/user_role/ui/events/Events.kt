package com.thomas200593.mdm.features.user_role.ui.events

import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity

sealed interface Events {
    sealed interface Session : Events {
        data class Loading(val ev : SessionEvent.Loading) : Session
        data class Invalid(val ev: SessionEvent.Invalid, val error: Error) : Session
        data class Valid(
            val ev: SessionEvent,
            val data: Triple<UserEntity, UserProfileEntity, SessionEntity>,
            val currentRole: RoleEntity?
        ) : Session
    }
    sealed interface TopBar : Events {
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
    }
}