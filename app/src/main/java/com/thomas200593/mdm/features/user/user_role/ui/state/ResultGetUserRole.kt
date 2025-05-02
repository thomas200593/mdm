package com.thomas200593.mdm.features.user.user_role.ui.state

import com.thomas200593.mdm.features.role.role.entity.RoleEntity

sealed interface ResultGetUserRole {
    data object Loading : ResultGetUserRole
    data class Success(val data: List<RoleEntity>) : ResultGetUserRole
    data class Error(val t: Throwable) : ResultGetUserRole
}