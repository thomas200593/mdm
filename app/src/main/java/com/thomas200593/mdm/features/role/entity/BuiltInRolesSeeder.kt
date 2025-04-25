package com.thomas200593.mdm.features.role.entity

import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.role.dao.DaoRole
import kotlinx.coroutines.flow.firstOrNull

object BuiltInRolesSeeder {
    const val SYSTEM_OWNER = Constants.STR_SYSTEM + Constants.STR_UNDERSCORE + "OWNER"
    const val SYSTEM_ADMIN = Constants.STR_SYSTEM + Constants.STR_UNDERSCORE + "ADMIN"
    val roles = listOf(
        RoleEntity(
            roleCode = SYSTEM_OWNER,
            roleType = RoleType.BuiltIn,
            label = "System Owner",
            description = "Role that own system",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = SYSTEM_ADMIN,
            roleType = RoleType.BuiltIn,
            label = "System Admin",
            description = "Role that manage day to day system",
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