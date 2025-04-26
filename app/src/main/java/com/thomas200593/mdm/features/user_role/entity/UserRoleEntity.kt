package com.thomas200593.mdm.features.user_role.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import com.thomas200593.mdm.features.role.entity.RoleEntity
import com.thomas200593.mdm.features.user.entity.UserEntity

@Entity(
    tableName = "user_role",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoleEntity::class,
            parentColumns = ["role_code"],
            childColumns = ["role_code"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]), Index(value = ["role_code"]),
        Index(value = ["user_id", "role_code"], unique = true)
    ]
)
data class UserRoleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_id") val seqId : Long = 0,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "role_code") val roleCode: String,
    @ColumnInfo(name = "is_active") val isActive : Boolean,
    @ColumnInfo(name = "audit_trail") override val auditTrail : AuditTrail = AuditTrail()
) : BaseEntity