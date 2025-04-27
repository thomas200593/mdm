package com.thomas200593.mdm.core.design_system.session.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable @Entity(
    tableName = "session",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"])]
)
data class SessionEntity(
    @PrimaryKey
    @ColumnInfo(name = "session_id") val sessionId: String = UUIDv7.generateAsString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "expires_at") val expiresAt: Long? = null,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity
class TypeConverterSession {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
    @TypeConverter fun toJson(session: SessionEntity): String? =
        runCatching { json.encodeToString(session) }.getOrNull()
    @TypeConverter fun fromJson(sessionString: String): SessionEntity? =
        runCatching { json.decodeFromString<SessionEntity>(sessionString) }.getOrNull()
}