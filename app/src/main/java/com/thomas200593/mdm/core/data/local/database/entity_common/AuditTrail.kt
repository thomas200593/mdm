package com.thomas200593.mdm.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mdm.core.design_system.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable data class AuditTrail(
    @SerialName("created_by") val createdBy: String = Constants.STR_SYSTEM,
    @SerialName("created_at") val createdAt: Long = Constants.NOW_EPOCH_SECOND,
    @SerialName("modifier_by") val modifiedBy: String = Constants.STR_SYSTEM,
    @SerialName("modified_at") val modifiedAt: Long = Constants.NOW_EPOCH_SECOND
)
class TypeConverterAuditTrail {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
    @TypeConverter fun toJson(auditTrail: AuditTrail): String? =
        runCatching { json.encodeToString(auditTrail) }.getOrNull()
    @TypeConverter fun fromJson(auditTrailString: String): AuditTrail? =
        runCatching { json.decodeFromString<AuditTrail>(auditTrailString) }.getOrNull()
}