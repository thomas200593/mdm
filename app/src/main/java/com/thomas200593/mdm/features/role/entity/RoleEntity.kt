package com.thomas200593.mdm.features.role.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "role")
data class RoleEntity (
    @PrimaryKey
    @ColumnInfo(name = "role_code") val roleCode: String,
    @ColumnInfo(name = "role_type") val roleType: RoleType,
    @ColumnInfo(name = "role_label") val label: String,
    @ColumnInfo(name = "description") val description: String = Constants.STR_EMPTY,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail
) : BaseEntity