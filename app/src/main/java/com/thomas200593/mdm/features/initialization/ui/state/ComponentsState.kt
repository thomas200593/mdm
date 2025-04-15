package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.conf.common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val resultInitializationState: ResultInitializationState
    ) : ComponentsState
}