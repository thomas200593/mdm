package com.thomas200593.mdm.features.role_selection.ui.state

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data class SessionInvalidDialog(val t : Throwable?) : DialogState
}
