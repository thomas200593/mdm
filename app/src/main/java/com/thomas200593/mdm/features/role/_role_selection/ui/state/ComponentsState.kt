package com.thomas200593.mdm.features.role._role_selection.ui.state

import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import com.thomas200593.mdm.features.conf.common.entity.Common

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