package com.thomas200593.mdm.features.role_selection.ui.state

import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType

data class FormRoleSelectionState(
    val fldSelectedRole : RoleEntity? = null,
    val fldLayoutMode: LayoutMode = LayoutMode.List,
    val fldCurrentFilter : RoleType? = null, // null = All
    val fldCurrentSort : SortOption = SortOption.LabelAsc, // default sort
    val btnProceedVisible : Boolean = false,
    val btnProceedEnabled : Boolean = false
) {
    fun setValue(
        selectedRole : RoleEntity? = null,
        layoutMode: LayoutMode? = null
    ) = copy(
        fldSelectedRole = selectedRole ?: fldSelectedRole,
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