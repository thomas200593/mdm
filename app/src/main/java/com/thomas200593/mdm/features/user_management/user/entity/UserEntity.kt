package com.thomas200593.mdm.features.user_management.user.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity

@Entity(
    tableName = "user",
    indices = [Index(value = ["email"], unique = true)] // Ensures email is unique
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity