package com.thomas200593.mdm.features.user_management.security.auth.ui.state

import com.thomas200593.mdm.features.common.cnf_common.entity.Common

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val resultSignIn: ResultSignIn
    ) : ComponentsState
}