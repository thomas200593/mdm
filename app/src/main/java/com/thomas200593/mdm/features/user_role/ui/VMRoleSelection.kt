package com.thomas200593.mdm.features.user_role.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.session.entity.DTOSessionUserData
import com.thomas200593.mdm.features.session.entity.SessionEvent
import com.thomas200593.mdm.features.user_role.domain.UCGetScreenData
import com.thomas200593.mdm.features.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_role.ui.state.ComponentsState
import com.thomas200593.mdm.features.user_role.ui.state.DialogState
import com.thomas200593.mdm.features.user_role.ui.state.ResultGetUserRole
import com.thomas200593.mdm.features.user_role.ui.state.ResultSetUserRole
import com.thomas200593.mdm.features.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.user_role.repository.RepoUserRole
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
    private val repoUserRole: RepoUserRole
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when (event) {
        is Events.Screen.Opened -> handleOnOpenEvent()
    }
    fun onSessionEvent(event: Events.Session) = when (event) {
        is Events.Session.Loading -> handleSessionLoading(event = event.ev)
        is Events.Session.Invalid -> handleSessionInvalid(event = event.ev, t = event.t)
        is Events.Session.NoCurrentRole -> handleSessionValid(event = event.ev, data = event.data)
        is Events.Session.Valid -> handleSessionValid(event = event.ev, data = event.data)
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
    }
    fun onDialogEvent(event: Events.Dialog) = when (event) {
        Events.Dialog.ErrorDismissed -> updateDialog { DialogState.None }
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
    private fun updateDialog(transform: (DialogState) -> DialogState) = updateUiState { it.copy(dialogState = transform(it.dialogState)) }
    private fun handleOnOpenEvent() = viewModelScope.launch {
        ucGetScreenData.invoke()
            .onStart { uiState.update { it.copy(componentsState = ComponentsState.Loading) } }
            .collect { confCommon ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = confCommon,
                        dialogState = DialogState.None,
                        sessionEvent = SessionEvent.Loading,
                        sessionData = null,
                        resultGetUserRole = ResultGetUserRole.Loading,
                        resultSetUserRole = ResultSetUserRole.Idle
                    )
                ) }
            }
    }
    private fun handleSessionLoading(event: SessionEvent.Loading) = updateUiState { it.copy(dialogState = DialogState.None, resultGetUserRole = ResultGetUserRole.Loading, sessionEvent = event, sessionData = null) }
    private fun handleSessionInvalid(event: SessionEvent.Invalid, t: Throwable) {
        updateUiState { it.copy(sessionEvent = event, sessionData = null, resultGetUserRole = ResultGetUserRole.Error(t)) }
        updateDialog { DialogState.SessionInvalidDialog(t) }
    }
    private fun handleSessionValid(event: SessionEvent, data: DTOSessionUserData) {
        updateUiState { it.copy(dialogState = DialogState.None, resultGetUserRole = ResultGetUserRole.Loading, sessionEvent = event, sessionData = data.session) }
        viewModelScope.launch {
            ucGetUserRole.invoke(data.user).collect { result -> result
                .onSuccess { roles -> updateUiState { it.copy(resultGetUserRole = ResultGetUserRole.Success(roles), sessionEvent = event, sessionData = data.session) } }
                .onFailure { err -> updateUiState { it.copy(resultGetUserRole = ResultGetUserRole.Error(err), sessionEvent = event) } }
            }
        }
    }
    fun testDeleteUserRole() = viewModelScope.launch { repoUserRole.deleteAll() }
}