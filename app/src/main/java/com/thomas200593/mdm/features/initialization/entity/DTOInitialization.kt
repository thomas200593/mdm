package com.thomas200593.mdm.features.initialization.entity

import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing user initialization details.
 *
 * This DTO is used to store and transfer user-related data during the initialization process.
 *
 * @property firstName The first name of the user.
 * @property lastName The last name of the user.
 * @property email The email address of the user.
 * @property authType The authentication type associated with the user.
 */
@Serializable
data class DTOInitialization(
    val firstName: String,
    val lastName: String,
    val email: String,
    val authType: AuthType
)
/**
 * Converts a [DTOInitialization] instance into a [UserEntity].
 *
 * This function is useful for mapping user initialization data into the database entity model.
 *
 * @param uid The unique identifier (UID) of the user.
 * @return A [UserEntity] containing the mapped user data.
 */
fun DTOInitialization.toUserEntity(uid: String) = UserEntity(
    uid = uid,
    email = this.email
)
/**
 * Converts a [DTOInitialization] instance into an [AuthEntity].
 *
 * This function maps user authentication details into the authentication entity model.
 *
 * @param uid The unique identifier (UID) of the user.
 * @return An [AuthEntity] containing authentication-related data.
 */
fun DTOInitialization.toAuthEntity(uid: String) = AuthEntity(
    userId = uid,
    authType = this.authType
)