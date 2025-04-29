package com.thomas200593.mdm.features.role_selection.ui.state

sealed interface ResultSetUserRole {
    data object Idle : ResultSetUserRole
    data object Loading : ResultSetUserRole
    data object Success : ResultSetUserRole
    data class Error(val t: Throwable) : ResultSetUserRole
}