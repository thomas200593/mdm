package com.thomas200593.mdm.features.bootstrap.ui.state

import com.thomas200593.mdm.features.cnf_common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(val confCommon: Common) : ComponentsState
}