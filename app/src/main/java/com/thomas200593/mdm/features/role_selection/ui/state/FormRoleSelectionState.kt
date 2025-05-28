package com.thomas200593.mdm.features.role_selection.ui.state

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
        fldSelectedRole = selectedRole ?: fldSelectedRole,
        fldSearchQuery = searchQuery ?: fldSearchQuery,
        fldLayoutMode = layoutMode ?: fldLayoutMode
    )
    companion object {
        enum class LayoutMode { List, Grid }
        enum class SortOption(val label: String) {
            LabelAsc("Label A–Z"),
            LabelDesc("Label Z–A"),
            TypeAsc("Role Type ↑"),
            TypeDesc("Role Type ↓"),
            CodeAsc("Role Code ↑"),
            CodeDesc("Role Code ↓"),
            CreatedOldest("Created Oldest"),
            CreatedNewest("Created Newest")
        }
    }
}