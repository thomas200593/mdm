package com.thomas200593.mdm.features.auth.ui.state

import com.thomas200593.mdm.features.cnf_common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val resultSignIn: ResultSignIn
    ) : ComponentsState
}