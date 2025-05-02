package com.thomas200593.mdm.features.role.role.entity

import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed interface RoleType {
    @Serializable
    @SerialName("BuiltIn")
    data object BuiltIn : RoleType
    @Serializable
    @SerialName("UserDefined")
    data object UserDefined : RoleType
}
class TypeConverterRoleType {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
    @TypeConverter fun toJson(roleType: RoleType): String? =
        runCatching { json.encodeToString(roleType) }.getOrNull()
    @TypeConverter fun fromJson(roleTypeString: String): RoleType? =
        runCatching { json.decodeFromString<RoleType>(roleTypeString) }.getOrNull()
}
