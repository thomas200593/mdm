package com.thomas200593.mdm.features.user_management.user_role.ui.state

import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEntity
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEvent
import com.thomas200593.mdm.features.common.cnf_common.entity.Common

sealed interface ScreenDataState {
    data object Loading : ScreenDataState
    data class Loaded(
        val confCommon : Common,
        val sessionEvent : SessionEvent,
        val sessionData : SessionEntity?,
        val resultGetUserRoles : ResultGetUserRolesState
    ) : ScreenDataState
}