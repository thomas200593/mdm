package com.thomas200593.mdm.features.auth.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import com.thomas200593.mdm.features.user.entity.UserEntity

@Entity(
    tableName = "auth",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"], unique = true)],
)
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_id") val seqId : Long = 0,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "auth_type") val authType: AuthType,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity