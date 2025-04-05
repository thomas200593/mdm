package com.thomas200593.mdm.features.initialization.ui.state

sealed interface DialogState {
    data object None : DialogState
    data object InfoScrDesc : DialogState
    data class Error(val t: Throwable) : DialogState
    data object SuccessInitialization : DialogState
}