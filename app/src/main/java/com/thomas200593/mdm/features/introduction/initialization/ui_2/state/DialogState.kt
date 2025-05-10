package com.thomas200593.mdm.features.introduction.initialization.ui_2.state

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data object LoadingDialog : DialogState
    data class ErrorDialog(val t: Throwable) : DialogState
    data object SuccessDialog : DialogState
}