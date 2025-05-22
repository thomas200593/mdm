package com.thomas200593.mdm.features.introduction.initialization.ui.state

import com.thomas200593.mdm.core.design_system.error.Error

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data object LoadingDialog : DialogState
    data class ErrorDialog(val t : Error) : DialogState
    data object SuccessDialog : DialogState
}