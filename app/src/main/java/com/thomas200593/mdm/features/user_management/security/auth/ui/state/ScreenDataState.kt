package com.thomas200593.mdm.features.user_management.security.auth.ui.state

import com.thomas200593.mdm.features.common.cnf_common.entity.Common

sealed interface ScreenDataState {
    data object Loading : ScreenDataState
    data class Loaded(
        val confCommon: Common
    ) : ScreenDataState
}