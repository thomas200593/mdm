package com.thomas200593.mdm.core.design_system.session.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.user.entity.UserEntity

@Entity(
    tableName = "session",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class SessionEntity(
    @PrimaryKey val sessionId: String = UUIDv7.generateAsString(),
    val userId: String,
    val expiresAt: Long? = null,
    override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity