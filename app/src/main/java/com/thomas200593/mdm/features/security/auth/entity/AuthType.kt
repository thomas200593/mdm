package com.thomas200593.mdm.features.security.auth.entity

import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed interface AuthType {
    @Serializable
    @SerialName("LocalEmailPassword")
    data class LocalEmailPassword(val provider: AuthProvider = AuthProvider.LOCAL_EMAIL_PASSWORD, val password: String) : AuthType
}
class TypeConverterAuthType {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
    @TypeConverter fun toJson(authType: AuthType): String? =
        runCatching { json.encodeToString(authType) }.getOrNull()
    @TypeConverter fun fromJson(authTypeString: String): AuthType? =
        runCatching { json.decodeFromString<AuthType>(authTypeString) }.getOrNull()
}