package com.thomas200593.mdm.features.session.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.base_class.BaseEntity

@Entity(tableName = "session_history")
data class SessionHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_id") val seqId: Long = 0,
    @ColumnInfo(name = "session") val session: SessionEntity,
    @ColumnInfo(name = "audit_trail") override val auditTrail: AuditTrail = AuditTrail()
) : BaseEntity