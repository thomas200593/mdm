package com.thomas200593.mdm.features.role_selection.ui.state

import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.role_selection.entity.DTORoleSelection

sealed interface ResultSetUserRoleState {
    data object Idle : ResultSetUserRoleState
    data object Loading : ResultSetUserRoleState
    data class Success(val dto : DTORoleSelection) : ResultSetUserRoleState
    data class Failure(val t : Error) : ResultSetUserRoleState
}