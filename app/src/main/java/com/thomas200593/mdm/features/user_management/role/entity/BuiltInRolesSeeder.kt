package com.thomas200593.mdm.features.user_management.role.entity

import com.thomas200593.mdm.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.user_management.role.dao.DaoRole
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
        ),
        /*TODO Test Only*/
        RoleEntity(
            roleCode = "ROLE_TEST_1",
            roleType = RoleType.BuiltIn,
            label = "Role Test 1",
            description = "Role test 1",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_2",
            roleType = RoleType.BuiltIn,
            label = "Role Test 2",
            description = "Role test 2",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_3",
            roleType = RoleType.BuiltIn,
            label = "Role Test 3",
            description = "Role test 3",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_4",
            roleType = RoleType.BuiltIn,
            label = "Role Test 4",
            description = "Role test 4",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_5",
            roleType = RoleType.BuiltIn,
            label = "Role Test 5",
            description = "Role test 5",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_6",
            roleType = RoleType.BuiltIn,
            label = "Role Test 6",
            description = "Role test 6",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_7",
            roleType = RoleType.BuiltIn,
            label = "Role Test 7",
            description = "Role test 7",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_8",
            roleType = RoleType.BuiltIn,
            label = "Role Test 8",
            description = "Role test 8",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_9",
            roleType = RoleType.BuiltIn,
            label = "Role Test 9",
            description = "Role test 9",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_10",
            roleType = RoleType.BuiltIn,
            label = "Role Test 10",
            description = "Role test 10",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_11",
            roleType = RoleType.BuiltIn,
            label = "Role Test 11",
            description = "Role test 11",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_12",
            roleType = RoleType.BuiltIn,
            label = "Role Test 12",
            description = "Role test 12",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_13",
            roleType = RoleType.BuiltIn,
            label = "Role Test 13",
            description = "Role test 13",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_14",
            roleType = RoleType.BuiltIn,
            label = "Role Test 14",
            description = "Role test 14",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_15",
            roleType = RoleType.BuiltIn,
            label = "Role Test 15",
            description = "Role test 15",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_16",
            roleType = RoleType.BuiltIn,
            label = "Role Test 16",
            description = "Role test 16",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_17",
            roleType = RoleType.BuiltIn,
            label = "Role Test 17",
            description = "Role test 17",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_18",
            roleType = RoleType.BuiltIn,
            label = "Role Test 18",
            description = "Role test 18",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_19",
            roleType = RoleType.BuiltIn,
            label = "Role Test 19",
            description = "Role test 19",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_20",
            roleType = RoleType.BuiltIn,
            label = "Role Test 20",
            description = "Role test 20",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_21",
            roleType = RoleType.BuiltIn,
            label = "Role Test 21",
            description = "Role test 21",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_22",
            roleType = RoleType.BuiltIn,
            label = "Role Test 22",
            description = "Role test 22",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_23",
            roleType = RoleType.BuiltIn,
            label = "Role Test 23",
            description = "Role test 23",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_24",
            roleType = RoleType.BuiltIn,
            label = "Role Test 24",
            description = "Role test 24",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_25",
            roleType = RoleType.BuiltIn,
            label = "Role Test 25",
            description = "Role test 25",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_26",
            roleType = RoleType.BuiltIn,
            label = "Role Test 26",
            description = "Role test 26",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_27",
            roleType = RoleType.BuiltIn,
            label = "Role Test 27",
            description = "Role test 27",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_28",
            roleType = RoleType.BuiltIn,
            label = "Role Test 28",
            description = "Role test 28",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_29",
            roleType = RoleType.BuiltIn,
            label = "Role Test 29",
            description = "Role test 29",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_30",
            roleType = RoleType.BuiltIn,
            label = "Role Test 30",
            description = "Role test 30",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_31",
            roleType = RoleType.BuiltIn,
            label = "Role Test 31",
            description = "Role test 31",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_32",
            roleType = RoleType.BuiltIn,
            label = "Role Test 32",
            description = "Role test 32",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_33",
            roleType = RoleType.BuiltIn,
            label = "Role Test 33",
            description = "Role test 33",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_34",
            roleType = RoleType.BuiltIn,
            label = "Role Test 34",
            description = "Role test 34",
            auditTrail = AuditTrail()
        ),
        RoleEntity(
            roleCode = "ROLE_TEST_35",
            roleType = RoleType.BuiltIn,
            label = "Role Test 35",
            description = "Role test 35",
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