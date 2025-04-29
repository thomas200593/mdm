package com.thomas200593.mdm.features.role_selection.ui.state

import com.thomas200593.mdm.features.conf.common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState
    ) : ComponentsState
}