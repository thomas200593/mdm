package com.thomas200593.mdm.features.auth.ui.state

import com.thomas200593.mdm.features.conf.common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common
    ) : ComponentsState
}