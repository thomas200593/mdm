package com.thomas200593.mdm.core.data.local.database.entity_common

import com.thomas200593.mdm.core.design_system.util.Constants
import kotlinx.serialization.Serializable

@Serializable
data class AuditTrail(
    val createdBy: String = Constants.STR_SYSTEM, val createdAt: Long = Constants.NOW_EPOCH_SECOND,
    val modifiedBy: String = Constants.STR_SYSTEM, val modifiedAt: Long = Constants.NOW_EPOCH_SECOND
)