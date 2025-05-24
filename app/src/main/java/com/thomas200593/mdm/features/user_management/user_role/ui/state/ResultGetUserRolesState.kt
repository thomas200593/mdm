package com.thomas200593.mdm.features.user_management.user_role.ui.state

import androidx.paging.PagingData
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity
import kotlinx.coroutines.flow.Flow

sealed interface ResultGetUserRolesState {
    data object Loading : ResultGetUserRolesState
    data class Success(val data: Flow<PagingData<RoleEntity>>) : ResultGetUserRolesState
    data class Failure(val t: Error) : ResultGetUserRolesState
}