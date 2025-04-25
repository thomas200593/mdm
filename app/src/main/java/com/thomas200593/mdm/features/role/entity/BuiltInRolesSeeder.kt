package com.thomas200593.mdm.features.role.entity

import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.role.dao.DaoRole
import kotlinx.coroutines.flow.firstOrNull

object BuiltInRolesSeeder {
    val roles = listOf(
        RoleEntity(
            roleCode = Constants.SYSTEM_OWNER,
            roleType = RoleType.BuiltIn,
            label = "System Owner",
            description = "Role that own system",
            auditTrail = AuditTrail()
        )
    )
    suspend fun patchBuiltInRolesIfMissing(dao: DaoRole) {
        TypeConverterRoleType().toJson(RoleType.BuiltIn)
            ?.let { json -> dao.getBuiltInRoles(json).firstOrNull()?.map { it.roleCode }?.toSet().orEmpty()
                .let { existingRoleCodes -> roles.filterNot { it.roleCode in existingRoleCodes } }
                .takeIf { it.isNotEmpty() }?.let { missingRoles -> dao.seedsBuiltInRoles(missingRoles) }
            }
    }
}