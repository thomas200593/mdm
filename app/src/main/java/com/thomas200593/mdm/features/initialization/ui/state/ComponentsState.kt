package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.role.entity.RoleEntity

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val initialSetOfRoles: Set<RoleEntity>,
        val resultInitialization: ResultInitialization
    ) : ComponentsState
}