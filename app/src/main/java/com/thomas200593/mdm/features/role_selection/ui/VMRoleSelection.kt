package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.management.user_role.domain.UCGetUserRoleCount
import com.thomas200593.mdm.features.management.user_role.entity.SortOption
import com.thomas200593.mdm.features.role_selection.domain.UCGetScreenData
import com.thomas200593.mdm.features.role_selection.domain.UCSetUserRole
import com.thomas200593.mdm.features.role_selection.entity.DTORoleSelection
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.FormRoleSelectionState
import com.thomas200593.mdm.features.role_selection.ui.state.ResultSetUserRoleState
import com.thomas200593.mdm.features.role_selection.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class VMRoleSelection @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucGetUserRole: UCGetUserRole,
    private val ucGetUserRoleCount: UCGetUserRoleCount,
    private val ucSetUserRole: UCSetUserRole
) : ViewModel() {
    data class UiState(
        val screenData : ScreenDataState = ScreenDataState.Loading,
        val dialog : DialogState = DialogState.None,
        val resultSetUserRole : ResultSetUserRoleState = ResultSetUserRoleState.Idle
    )
    var uiState = MutableStateFlow(UiState()) ; private set
    var formRoleSelection by mutableStateOf(FormRoleSelectionState()) ; private set
    private var debounceJob: Job? = null
    private val debounceDelay: Long = 300L // adjust as needed
    fun onSessionEvent(event : Events.Session) = when (event) {
        is Events.Session.Loading -> handleSessionLoading()
        is Events.Session.Invalid -> handleSessionInvalid(event)
        is Events.Session.Valid -> handleSessionValid(event)
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed -> updateDialog { DialogState.None }
        is Events.TopBar.BtnSignOut.Clicked -> handleSignOut()
    }
    fun onFormEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.SelectedRole -> handleRoleSelection(event.role)
        is Events.Content.Form.SearchBar.QueryChanged -> {
            updateForm { it.setValue(searchQuery = event.query, selectedRole = null) }
            handleSearchBarQueryField()
        }
        is Events.Content.Form.LayoutType.Grid -> handleLayoutType(FormRoleSelectionState.Companion.LayoutMode.Grid)
        is Events.Content.Form.LayoutType.List -> handleLayoutType(FormRoleSelectionState.Companion.LayoutMode.List)
        is Events.Content.Form.ModalBottomSheet.Clicked -> {/*TODO*/}
        is Events.Content.Form.ModalBottomSheet.Applied -> {/*TODO*/}
        is Events.Content.Form.ModalBottomSheet.Dismissed -> {/*TODO*/}
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when (event) {
        is Events.BottomBar.BtnConfirmRole.Clicked -> handleConfirmRole(event.role)
        is Events.BottomBar.BtnRoleInfo.Clicked -> updateDialog { DialogState.RoleInfo(event.role) }
        is Events.BottomBar.BtnRoleInfo.Dismissed -> updateDialog { DialogState.None }
    }
    private fun handleSessionLoading() {
        formRoleSelection = FormRoleSelectionState()
        uiState.update { it.copy(
            screenData = ScreenDataState.Loading,
            resultSetUserRole = ResultSetUserRoleState.Idle
        ) }
    }
    private fun handleSessionInvalid(event : Events.Session.Invalid) {
        formRoleSelection = FormRoleSelectionState()
        uiState.update { it.copy(
            dialog = DialogState.SessionInvalid(error = event.error),
            resultSetUserRole = ResultSetUserRoleState.Idle
        ) }
    }
    private fun handleSessionValid(event : Events.Session.Valid) = viewModelScope.launch {
        val user = event.data.first
        val rolesFlow = ucGetUserRole.invoke(
            user = user,
            query = formRoleSelection.fldSearchQuery,
            sortOption = when (formRoleSelection.fldCurrentSort) {
                FormRoleSelectionState.Companion.SortOption.LabelAsc -> SortOption.RoleLabelAsc
                FormRoleSelectionState.Companion.SortOption.LabelDesc -> SortOption.RoleLabelDesc
                FormRoleSelectionState.Companion.SortOption.CodeAsc -> SortOption.RoleCodeAsc
                FormRoleSelectionState.Companion.SortOption.CodeDesc -> SortOption.RoleCodeDesc
                FormRoleSelectionState.Companion.SortOption.TypeAsc -> SortOption.RoleTypeAsc
                FormRoleSelectionState.Companion.SortOption.TypeDesc -> SortOption.RoleTypeDesc
            },
            filterOption = formRoleSelection.fldCurrentFilter
        )
        combine (
            flow = ucGetScreenData.invoke(), flow2 = ucGetUserRoleCount.invoke(user = user)
        ) { confCommon, userRolesCount -> confCommon to userRolesCount}.collect { (confCommon, userRolesCount) ->
            uiState.update {
                it.copy(
                    screenData = ScreenDataState.Loaded(
                        confCommon = confCommon,
                        sessionEvent = event.ev,
                        userData = user,
                        sessionData = event.data.third,
                        userRolesCount = userRolesCount,
                        roles = rolesFlow
                    ),
                    resultSetUserRole = ResultSetUserRoleState.Idle
                )
            }
        }
    }
    private fun handleRoleSelection(role: RoleEntity) =
        updateForm { it.setValue(selectedRole = role).validateSelection() }
    private fun handleLayoutType(layoutMode: FormRoleSelectionState.Companion.LayoutMode) =
        updateForm { it.setValue(layoutMode = layoutMode) }
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        uiState.update { it.copy(dialog = transform(it.dialog)) }
    private fun updateForm(transform: (FormRoleSelectionState) -> FormRoleSelectionState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            val updated = transform(formRoleSelection)
            formRoleSelection.takeIf { it != updated }?.let { formRoleSelection = updated }
        }
    private fun handleSearchBarQueryField() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(debounceDelay)
            val currentSession = (uiState.value.screenData as? ScreenDataState.Loaded) ?: return@launch
            currentSession.userData?.let { user ->
                val rolesFlow = ucGetUserRole.invoke(
                    user = user,
                    query = formRoleSelection.fldSearchQuery,
                    sortOption = when (formRoleSelection.fldCurrentSort) {
                        FormRoleSelectionState.Companion.SortOption.LabelAsc -> SortOption.RoleLabelAsc
                        FormRoleSelectionState.Companion.SortOption.LabelDesc -> SortOption.RoleLabelDesc
                        FormRoleSelectionState.Companion.SortOption.CodeAsc -> SortOption.RoleCodeAsc
                        FormRoleSelectionState.Companion.SortOption.CodeDesc -> SortOption.RoleCodeDesc
                        FormRoleSelectionState.Companion.SortOption.TypeAsc -> SortOption.RoleTypeAsc
                        FormRoleSelectionState.Companion.SortOption.TypeDesc -> SortOption.RoleTypeDesc
                    },
                    filterOption = formRoleSelection.fldCurrentFilter
                )
                uiState.update {
                    it.copy(screenData = currentSession.copy(roles = rolesFlow))
                }
            }
        }
    }
    private fun handleSignOut() = uiState.update { it.copy(
        screenData = ScreenDataState.Loading, dialog = DialogState.None, resultSetUserRole = ResultSetUserRoleState.Idle
    ) }
    private fun handleConfirmRole(role : RoleEntity) = viewModelScope.launch {
        val loaded = uiState.value.screenData as? ScreenDataState.Loaded ?: return@launch
        if(uiState.value.resultSetUserRole == ResultSetUserRoleState.Loading) return@launch
        updateForm { it.setValue(selectedRole = role) }
        val frozenForm = formRoleSelection.disableInputs()/*TODO*/
        formRoleSelection = frozenForm
        when {
            loaded.userData == null -> {
                val error = Error.Input.MalformedError(message = "User is not authorized to set role")
                handleSetRoleFailure(error)
                return@launch
            }
            loaded.sessionData == null -> {
                val error = Error.Input.MalformedError(message = "Your session is invalid / expired, please re-log")
                handleSetRoleFailure(error)
                return@launch
            }
            else -> {
                val dto = DTORoleSelection(user = loaded.userData, session = loaded.sessionData, role = role)
                uiState.update { it.copy(resultSetUserRole = ResultSetUserRoleState.Loading) }
                ucSetUserRole.invoke(dto).fold(
                    onFailure = { err ->
                        val error = err as? Error ?: Error.UnknownError(message = err.message, cause = err.cause)
                        handleSetRoleFailure(error)
                    },
                    onSuccess = { result -> /*TODO*/ }
                )
            }
        }
    }
    private fun handleSetRoleFailure(error: Error) {
        uiState.update { it.copy(resultSetUserRole = ResultSetUserRoleState.Failure(error)) }
        formRoleSelection = FormRoleSelectionState().validateSelection()
    }
}