package com.thomas200593.mdm.features.user.initialization.entity

import com.thomas200593.mdm.features.security.auth.entity.AuthEntity
import com.thomas200593.mdm.features.user.user.entity.UserEntity
import com.thomas200593.mdm.features.user.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.user.user_role.entity.UserRoleEntity

data class DTOInitializationResult(
    val userId: Long, val user: UserEntity,
    val profileId: Long, val profile: UserProfileEntity,
    val authId: Long, val auth: AuthEntity,
    val rolesIds: List<Long>, val roles: List<UserRoleEntity>
)