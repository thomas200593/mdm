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
        val typeConverter = TypeConverterRoleType()
        val builtInJson = typeConverter.toJson(RoleType.BuiltIn) ?: return
        val existingRoleCodes = dao.getBuiltInRoles(builtInJson).firstOrNull()?.map { it.roleCode }?.toSet() ?: emptySet()
        val missingRoles = roles.filterNot { it.roleCode in existingRoleCodes }
        if (missingRoles.isNotEmpty()) dao.insertBuiltInRoles(missingRoles)
    }
}