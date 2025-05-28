package com.thomas200593.mdm.features.management.user_role.entity

import com.thomas200593.mdm.features.management.role.entity.RoleType

enum class FilterOption (val roleType: RoleType?) {
    RoleTypeAll(roleType = null),
    RoleTypeBuiltIn(roleType = RoleType.BuiltIn), RoleTypeUserDefined(roleType = RoleType.UserDefined)
}