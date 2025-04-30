package com.thomas200593.mdm.features.role_selection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.session.entity.DTOSessionUserData
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.features.role_selection.domain.UCGetScreenData
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.ComponentsState
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.ResultGetUserRole
import com.thomas200593.mdm.features.role_selection.ui.state.ResultSetUserRole
import com.thomas200593.mdm.features.user.repository.RepoUser
import com.thomas200593.mdm.features.user_role.domain.UCGetUserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucGetUserRole: UCGetUserRole,
    private val repoSession: RepoSession,
    private val repoUser: RepoUser
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when (event) {
        is Events.Screen.Opened -> handleOnOpenEvent()
    }
    private inline fun updateUiState(crossinline transform: (ComponentsState.Loaded) -> ComponentsState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            uiState.update { current ->
                (current.componentsState as? ComponentsState.Loaded)
                    ?. let(transform)
                    ?. let { updatedState -> current.copy(componentsState = updatedState) }
                    ?: current
            }
        }
    private fun handleOnOpenEvent() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart { uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
            .collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon,
                        dialogState = DialogState.None,
                        resultGetUserRole = ResultGetUserRole.Loading,
                        resultSetUserRole = ResultSetUserRole.Idle,
                        sessionEvent = SessionEvent.Loading
                    )
                ) }
            }
    }
    private fun updateDialog(transform: (DialogState) -> DialogState) = updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun handleSessionLoading(event: SessionEvent) = updateUiState { it.copy(dialogState = DialogState.None, resultGetUserRole = ResultGetUserRole.Loading, sessionEvent = event) }
    private fun handleSessionInvalid(event: SessionEvent, throwable: Throwable?) {
        updateUiState { it.copy(
            sessionEvent = event,
            resultGetUserRole = ResultGetUserRole.Error(throwable)
        ) }
        updateDialog { DialogState.SessionInvalidDialog(throwable) }
    }
    private fun handleSessionValid(event: SessionEvent, data: DTOSessionUserData?) {
        updateUiState { it.copy(dialogState = DialogState.None, resultGetUserRole = ResultGetUserRole.Loading, sessionEvent = event) }
        data?.let { data ->
            viewModelScope.launch {
                ucGetUserRole.invoke(data.user).collect { result -> result
                    .onSuccess { roles -> updateUiState { it.copy(resultGetUserRole = ResultGetUserRole.Success(roles), sessionEvent = event) } }
                    .onFailure { err -> updateUiState { it.copy(resultGetUserRole = ResultGetUserRole.Error(err), sessionEvent = event) } }
                }
            } }
            ?: updateUiState { it.copy(resultGetUserRole = ResultGetUserRole.Error(Error.Input.MalformedError(message = "Cannot get empty user")), sessionEvent = event) }
    }
    fun testDeleteUser() = viewModelScope.launch {
        repoUser.deleteAll()
    }
    fun testDeleteSession() = viewModelScope.launch {
        repoSession.deleteAll()
    }
}