package com.thomas200593.mdm.features.introduction.initialization.ui_2.state

import com.thomas200593.mdm.features.common.cnf_common.entity.Common
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common,
        val dialogState: DialogState,
        val initialSetOfRoles: Set<RoleEntity>,
        val resultInitialization: ResultInitialization
    ) : ComponentsState
}