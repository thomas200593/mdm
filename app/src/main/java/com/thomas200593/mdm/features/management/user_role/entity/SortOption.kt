package com.thomas200593.mdm.features.management.user_role.entity

import androidx.annotation.StringRes
import com.thomas200593.mdm.R

enum class SortOption(@StringRes val label : Int) {
    RoleLabelAsc(label = R.string.str_user_role_sort_opt_label_asc),
    RoleLabelDesc(label = R.string.str_user_role_sort_opt_label_desc),
    RoleTypeAsc(label = R.string.str_user_role_sort_opt_role_type_asc),
    RoleTypeDesc(label = R.string.str_user_role_sort_opt_role_type_desc),
    RoleCodeAsc(label = R.string.str_user_role_sort_opt_role_code_asc),
    RoleCodeDesc(label = R.string.str_user_role_sort_opt_role_code_desc),
    RoleCreatedOldest(label = R.string.str_user_role_sort_opt_audit_created_asc),
    RoleCreatedNewest(label = R.string.str_user_role_sort_opt_audit_created_desc)
}