package com.thomas200593.mdm.features.user_role.ui.state

import com.thomas200593.mdm.core.design_system.error.Error

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data class SessionInvalid(val error : Error) : DialogState
}
