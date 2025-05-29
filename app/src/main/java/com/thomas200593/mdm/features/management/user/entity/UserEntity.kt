package com.thomas200593.mdm.features.management.user.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import kotlinx.serialization.Serializable

@Serializable @Entity(
    tableName = "user",
    indices = [Index(value = ["email"], unique = true)] // Ensures email is unique
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity