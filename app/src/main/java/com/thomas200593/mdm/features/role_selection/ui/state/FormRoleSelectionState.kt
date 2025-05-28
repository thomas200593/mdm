package com.thomas200593.mdm.features.role_selection.ui.state

import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType
import com.thomas200593.mdm.features.management.user.entity.UserEntity

data class FormRoleSelectionState(
    val fldUser : UserEntity? = null,
    val fldSelectedRole : RoleEntity? = null,
    val fldSearchQuery: String = Constants.STR_EMPTY,
    val fldLayoutMode: LayoutMode = LayoutMode.List,
    val fldCurrentFilter : RoleType? = null, // null = All
    val fldCurrentSort : SortOption = SortOption.LabelAsc, // default sort
    val btnProceedVisible : Boolean = false,
    val btnProceedEnabled : Boolean = false
) {
    fun setValue(
        user : UserEntity? = null,
        selectedRole : RoleEntity? = null,
        searchQuery : String? = null,
        layoutMode : LayoutMode? = null
    ) = copy(
        fldUser = user ?: fldUser,
        fldSelectedRole = selectedRole,
        fldSearchQuery = searchQuery ?: fldSearchQuery,
        fldLayoutMode = layoutMode ?: fldLayoutMode
    )
    companion object {
        enum class LayoutMode { List, Grid }
        enum class SortOption(@StringRes val label : Int) {
            LabelAsc(R.string.str_user_role_sort_opt_label_asc),
            LabelDesc(R.string.str_user_role_sort_opt_label_desc),
            TypeAsc(R.string.str_user_role_sort_opt_role_type_asc),
            TypeDesc(R.string.str_user_role_sort_opt_role_type_desc),
            CodeAsc(R.string.str_user_role_sort_opt_role_code_asc),
            CodeDesc(R.string.str_user_role_sort_opt_role_code_desc),
            /*CreatedOldest(R.string.str_user_role_sort_opt_audit_created_asc),
            CreatedNewest(R.string.str_user_role_sort_opt_audit_created_desc)*/
        }
    }
}