package com.thomas200593.mdm.features.user_management.user_role.ui.state

import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEntity
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEvent
import com.thomas200593.mdm.features.common.cnf_common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val sessionEvent: SessionEvent,
        val sessionData: SessionEntity?,
        val resultGetUserRole: ResultGetUserRole,
        val resultSetUserRole: ResultSetUserRole
    ) : ComponentsState
}