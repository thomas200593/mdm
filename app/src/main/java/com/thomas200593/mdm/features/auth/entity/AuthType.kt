package com.thomas200593.mdm.features.auth.entity

import androidx.room.TypeConverter
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed interface AuthType {
    @Serializable
    data class LocalEmailPassword(val provider: String, val password: String) : AuthType
    @Serializable
    data class OAuth(val provider: String, val token: String) : AuthType
}

class TypeConverterAuthType {
    private val json = Json { ignoreUnknownKeys = true }
    @TypeConverter
    fun toJson(authType: AuthType): String {
        return json.encodeToString(authType)
    }
    @TypeConverter
    fun fromJson(authTypeString: String): AuthType {
        return json.decodeFromString(authTypeString)
    }
}

fun AuthType.LocalEmailPassword.toUserEntity(uid: String, email: String) = UserEntity(
    uid = uid,
    email = email
)