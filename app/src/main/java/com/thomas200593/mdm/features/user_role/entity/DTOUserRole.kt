package com.thomas200593.mdm.features.user_role.entity

import com.thomas200593.mdm.features.role.entity.RoleEntity
import com.thomas200593.mdm.features.user.entity.UserEntity

data class DTOUserRole(
    val user: UserEntity,
    val roles: List<RoleEntity>
)