package com.thomas200593.mdm.features.role_selection.entity

import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import kotlinx.serialization.Serializable

@Serializable data class DTORoleSelection(
    val user : UserEntity,
    val session : SessionEntity,
    val role : RoleEntity
)