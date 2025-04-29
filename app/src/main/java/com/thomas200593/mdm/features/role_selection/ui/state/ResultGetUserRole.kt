package com.thomas200593.mdm.features.role_selection.ui.state

sealed interface ResultGetUserRole {
    data object Loading : ResultGetUserRole
    data class Success(val data: Any) : ResultGetUserRole
    data class Error(val t: Throwable) : ResultGetUserRole
}