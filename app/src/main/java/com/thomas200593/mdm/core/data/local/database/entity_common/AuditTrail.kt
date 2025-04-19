package com.thomas200593.mdm.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mdm.core.design_system.util.Constants
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class AuditTrail(
    val createdBy: String = Constants.STR_SYSTEM, val createdAt: Long = Constants.NOW_EPOCH_SECOND,
    val modifiedBy: String = Constants.STR_SYSTEM, val modifiedAt: Long = Constants.NOW_EPOCH_SECOND
)
class TypeConverterAuditTrail {
    private val json = Json { ignoreUnknownKeys = true }
    @TypeConverter
    fun toJson(auditTrail: AuditTrail): String? = runCatching { json.encodeToString(auditTrail) }.getOrNull()
    @TypeConverter
    fun fromJson(auditTrailString: String): AuditTrail? = runCatching { json.decodeFromString<AuditTrail>(auditTrailString) }.getOrNull()
}