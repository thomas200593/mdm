package com.thomas200593.mdm.features.role_selection.ui.state

import androidx.paging.PagingData
import com.thomas200593.mdm.features.common.cnf_common.entity.Common
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import kotlinx.coroutines.flow.Flow

sealed interface ScreenDataState {
    data object Loading : ScreenDataState
    data class Loaded(
        val confCommon : Common,
        val sessionEvent : SessionEvent,
        val sessionData : SessionEntity?,
        val roles : Flow<PagingData<RoleEntity>>,
        val isRefreshing : Boolean = false,
        val layoutMode : LayoutMode = LayoutMode.Grid,
        val selectedRole : RoleEntity? = null
    ) : ScreenDataState
}
enum class LayoutMode { List, Grid }