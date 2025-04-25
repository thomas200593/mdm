package com.thomas200593.mdm.features.initialization.entity

import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import kotlinx.serialization.Serializable

@Serializable
data class DTOInitialization(
    val firstName: String,
    val lastName: String,
    val email: String,
    val authType: AuthType
)
fun DTOInitialization.toUserEntity(uid: String) = UserEntity(
    uid = uid,
    email = this.email
)
fun DTOInitialization.toAuthEntity(uid: String) = AuthEntity(
    userId = uid,
    authType = this.authType
)
fun DTOInitialization.toUserProfileEntity(uid: String) = UserProfileEntity(
    userId = uid,
    firstName = this.firstName,
    lastName = this.lastName
)