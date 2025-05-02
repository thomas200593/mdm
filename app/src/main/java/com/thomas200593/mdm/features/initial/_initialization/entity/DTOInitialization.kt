package com.thomas200593.mdm.features.initial._initialization.entity

import com.thomas200593.mdm.features.security.auth.entity.AuthEntity
import com.thomas200593.mdm.features.security.auth.entity.AuthType
import com.thomas200593.mdm.features.role.entity.RoleEntity
import com.thomas200593.mdm.features.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.user_role.entity.UserRoleEntity
import kotlinx.serialization.Serializable

@Serializable data class DTOInitialization(
    val firstName: String, val lastName: String, val email: String, val authType: AuthType,
    val initialSetOfRoles: Set<RoleEntity>
)
fun DTOInitialization.toUserEntity(uid: String) = UserEntity(uid = uid, email = this.email)
fun DTOInitialization.toAuthEntity(uid: String) = AuthEntity(userId = uid, authType = this.authType)
fun DTOInitialization.toUserProfileEntity(uid: String) = UserProfileEntity(userId = uid, firstName = this.firstName, lastName = this.lastName)
fun DTOInitialization.toUserRoleEntity(uid: String, roles: Set<RoleEntity>) : Set<UserRoleEntity> =
    roles.map { UserRoleEntity(userId = uid, roleCode = it.roleCode, isActive = true) }.toSet()