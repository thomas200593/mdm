package com.thomas200593.mdm.features.auth.ui.state

sealed interface DialogState {
    data object None : DialogState
    data object ScrDescDialog : DialogState
    data object LoadingAuthDialog : DialogState
    data object LoadingSessionDialog : DialogState
}