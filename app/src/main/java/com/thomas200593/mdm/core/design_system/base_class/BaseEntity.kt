package com.thomas200593.mdm.core.design_system.base_class

import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail

interface BaseEntity {
    val auditTrail: AuditTrail
}