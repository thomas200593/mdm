package com.thomas200593.mdm.features.introduction.initialization.ui.state

import com.thomas200593.mdm.features.common.cnf_common.entity.Common
import com.thomas200593.mdm.features.management.role.entity.RoleEntity

sealed interface ScreenDataState {
    data object Loading : ScreenDataState
    data class Loaded(
        val confCommon: Common,
        val initialSetOfRoles: Set<RoleEntity>
    ) : ScreenDataState
}