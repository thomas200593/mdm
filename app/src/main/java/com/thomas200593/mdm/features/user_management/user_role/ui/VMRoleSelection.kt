package com.thomas200593.mdm.features.user_management.user_role.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.features.user_management.user_role.domain.UCGetScreenData
import com.thomas200593.mdm.features.user_management.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.user_management.user_role.repository.RepoUserRole
import com.thomas200593.mdm.features.user_management.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_management.user_role.ui.state.DialogState
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ResultSetUserRoleState
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucGetUserRole: UCGetUserRole,
    private val repoUserRole: RepoUserRole
) : ViewModel() {
    data class UiState(
        val screenData : ScreenDataState = ScreenDataState.Loading,
        val dialog : DialogState = DialogState.None,
        val resultSetUserRole : ResultSetUserRoleState = ResultSetUserRoleState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    fun onSessionEvent(event: Events.Session) = when (event) {
        is Events.Session.Loading -> { uiState.update { it.copy(screenData = ScreenDataState.Loading) } }
        is Events.Session.Invalid -> {}
        is Events.Session.NoCurrentRole -> {}
        is Events.Session.Valid -> {}
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> {}
        is Events.TopBar.BtnScrDesc.Dismissed -> {}
    }
}