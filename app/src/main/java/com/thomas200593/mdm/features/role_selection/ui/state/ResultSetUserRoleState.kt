package com.thomas200593.mdm.features.role_selection.ui.state

sealed interface ResultSetUserRoleState {
    data object Idle : ResultSetUserRoleState
    data object Loading : ResultSetUserRoleState
    data object Success : ResultSetUserRoleState
    data class Error(val t: Throwable) : ResultSetUserRoleState
}