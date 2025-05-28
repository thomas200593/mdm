package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.role_selection.domain.UCGetScreenData
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.FormRoleSelectionState
import com.thomas200593.mdm.features.role_selection.ui.state.ResultSetUserRoleState
import com.thomas200593.mdm.features.role_selection.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucGetUserRole: UCGetUserRole
) : ViewModel() {
    data class UiState(
        val screenData : ScreenDataState = ScreenDataState.Loading,
        val dialog : DialogState = DialogState.None,
        val resultSetUserRole : ResultSetUserRoleState = ResultSetUserRoleState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    var formRoleSelection by mutableStateOf(FormRoleSelectionState()) ; private set
    fun onSessionEvent(event : Events.Session) = when (event) {
        is Events.Session.Loading -> handleSessionLoading()
        is Events.Session.Invalid -> handleSessionInvalid(event)
        is Events.Session.Valid -> handleSessionValid(event)
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> {}
        is Events.TopBar.BtnScrDesc.Dismissed -> {}
    }
    fun onSelectedRole(role: RoleEntity) {
        (uiState.value.screenData as? ScreenDataState.Loaded) ?.let { loaded ->
            formRoleSelection = formRoleSelection.setValue(selectedRole = role)
        } ?: return
    }
    private fun handleSessionLoading() {
        formRoleSelection = FormRoleSelectionState()
        uiState.update {
            it.copy(
                screenData = ScreenDataState.Loading,
                resultSetUserRole = ResultSetUserRoleState.Idle
            )
        }
    }
    private fun handleSessionInvalid(event : Events.Session.Invalid) {
        formRoleSelection = FormRoleSelectionState()
        uiState.update {
            it.copy(
                dialog = DialogState.SessionInvalid(error = event.error),
                resultSetUserRole = ResultSetUserRoleState.Idle
            )
        }
    }
    private fun handleSessionValid(event : Events.Session.Valid) = viewModelScope.launch {
        val user = event.data.first
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