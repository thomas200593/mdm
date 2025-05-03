package com.thomas200593.mdm.features.user_management.user_role.ui.state

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data class SessionInvalidDialog(val t : Throwable?) : DialogState
}
