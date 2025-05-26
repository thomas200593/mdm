package com.thomas200593.mdm.features.user_management.user_role.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity
import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSession
import com.thomas200593.mdm.features.user_management.user.entity.UserEntity
import com.thomas200593.mdm.features.user_management.user_role.domain.UCGetScreenData
import com.thomas200593.mdm.features.user_management.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.user_management.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_management.user_role.ui.state.DialogState
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ResultSetUserRoleState
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucGetUserRole: UCGetUserRole,
    private val repoSession: RepoSession
) : ViewModel() {
    private var currentUser: UserEntity? = null
    data class UiState(
        val screenData : ScreenDataState = ScreenDataState.Loading,
        val dialog : DialogState = DialogState.None,
        val resultSetUserRole : ResultSetUserRoleState = ResultSetUserRoleState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    fun onSessionEvent(event : Events.Session) = when (event) {
        is Events.Session.Loading -> {
            uiState.update {
                it.copy(
                    screenData = ScreenDataState.Loading,
                    resultSetUserRole = ResultSetUserRoleState.Idle
                )
            }
        }
        is Events.Session.Invalid -> {
            uiState.update {
                it.copy(
                    dialog = DialogState.SessionInvalid(error = event.error),
                    resultSetUserRole = ResultSetUserRoleState.Idle
                )
            }
        }
        is Events.Session.Valid -> viewModelScope.launch {
            val user = event.data.first
            currentUser = user
            val rolesFlow = ucGetUserRole.invoke(user)
            ucGetScreenData.invoke().collect { confCommon ->
                uiState.update {
                    it.copy(
                        screenData = ScreenDataState.Loaded(
                            confCommon = confCommon,
                            sessionEvent = event.ev,
                            sessionData = event.data.third,
                            roles = rolesFlow
                        ),
                        resultSetUserRole = ResultSetUserRoleState.Idle
                    )
                }
            }
        }
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> {}
        is Events.TopBar.BtnScrDesc.Dismissed -> {}
    }
    fun onSelectedRole(role: RoleEntity) {
        (uiState.value.screenData as? ScreenDataState.Loaded) ?.let { loaded ->
            uiState.update {
                it.copy(
                    screenData = loaded.copy(
                        selectedRole = role
                    )
                )
            }
        } ?: return
    }
    fun deleteSession() = viewModelScope.launch { repoSession.deleteAll() }
}