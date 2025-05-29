package com.thomas200593.mdm.features.role_selection.ui.state

import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.management.user_role.entity.FilterOption

data class FormRoleSelectionState(
    val fldUser : UserEntity? = null,
    val fldSelectedRole : RoleEntity? = null,
    val fldSearchQuery: String = Constants.STR_EMPTY,
    val fldLayoutMode: LayoutMode = LayoutMode.List,
    val fldCurrentFilter : FilterOption = FilterOption.RoleTypeAll, // null = All
    val fldCurrentSort : SortOption = SortOption.LabelAsc, // default sort
    val btnProceedVisible : Boolean = false,
    val btnProceedEnabled : Boolean = false
) {
    fun setValue(
        user : UserEntity? = null,
        selectedRole : RoleEntity? = null,
        searchQuery : String? = null,
        layoutMode : LayoutMode? = null,
        filterOption: FilterOption? = null,
        sortOption: SortOption? = null
    ) = copy(
        fldUser = user ?: fldUser,
        fldSelectedRole = selectedRole,
        fldSearchQuery = searchQuery ?: fldSearchQuery,
        fldLayoutMode = layoutMode ?: fldLayoutMode,
        fldCurrentFilter = filterOption ?: fldCurrentFilter,
        fldCurrentSort = sortOption ?: fldCurrentSort
    )
    fun validateSelection(session : SessionEntity) = copy(btnProceedVisible = canProceed(session), btnProceedEnabled = canProceed(session))
    private fun canProceed(session: SessionEntity) = fldUser?.uid == session.userId && fldSelectedRole != null
    companion object {
        enum class LayoutMode { List, Grid }
        enum class SortOption(@StringRes val label : Int) {
            LabelAsc(R.string.str_user_role_sort_opt_label_asc),
            LabelDesc(R.string.str_user_role_sort_opt_label_desc),
            TypeAsc(R.string.str_user_role_sort_opt_role_type_asc),
            TypeDesc(R.string.str_user_role_sort_opt_role_type_desc),
            CodeAsc(R.string.str_user_role_sort_opt_role_code_asc),
            CodeDesc(R.string.str_user_role_sort_opt_role_code_desc),
            /*TODO Ignore, CreatedOldest(R.string.str_user_role_sort_opt_audit_created_asc),
            CreatedNewest(R.string.str_user_role_sort_opt_audit_created_desc)*/
        }
    }
}