package com.thomas200593.mdm.features.role_selection.ui.state

import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.role.entity.RoleEntity

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data class RoleInfo(val role : RoleEntity) : DialogState
    data class SessionInvalid(val error : Error) : DialogState
}