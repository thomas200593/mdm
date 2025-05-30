package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.user.entity.UserEntity
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
        val screenData: ScreenDataState = ScreenDataState.Loading,
        val dialog: DialogState = DialogState.None,
        val resultSetUserRole: ResultSetUserRoleState = ResultSetUserRoleState.Idle
    )
    var uiState = MutableStateFlow(UiState()); private set
    var formRoleSelection by mutableStateOf(FormRoleSelectionState()); private set
    private var debounceJob: Job? = null
    private val debounceDelay = 300L
    fun onSessionEvent(event: Events.Session) = when (event) {
        is Events.Session.Loading -> resetToLoadingState()
        is Events.Session.Invalid -> showSessionInvalidDialog(event)
        is Events.Session.Valid -> loadSessionData(event)
    }
    fun onTopBarEvent(event: Events.TopBar) = when (event) {
        is Events.TopBar.BtnScrDesc.Clicked -> updateDialog { DialogState.ScrDescDialog }
        is Events.TopBar.BtnScrDesc.Dismissed,
        is Events.TopBar.BtnSignOut.Clicked -> resetToLoadingState()
    }
    fun onFormEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.SelectedRole -> updateSelectedRole(event.role)
        is Events.Content.Form.SearchBar.QueryChanged -> handleSearchQueryChange(event.query)
        is Events.Content.Form.LayoutType.Grid -> updateLayoutMode(FormRoleSelectionState.Companion.LayoutMode.Grid)
        is Events.Content.Form.LayoutType.List -> updateLayoutMode(FormRoleSelectionState.Companion.LayoutMode.List)
        is Events.Content.Form.ModalBottomSheet.Clicked -> {/*TODO*/}
        is Events.Content.Form.ModalBottomSheet.Applied -> {/*TODO*/}
        is Events.Content.Form.ModalBottomSheet.Dismissed -> {/*TODO*/}
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when (event) {
        is Events.BottomBar.BtnConfirmRole.Clicked -> confirmRoleSelection(event.role)
        is Events.BottomBar.BtnRoleInfo.Clicked -> updateDialog { DialogState.RoleInfo(event.role) }
        is Events.BottomBar.BtnRoleInfo.Dismissed -> updateDialog { DialogState.None }
    }
    fun onUserRoleSetCallBackEvent(event: Events.Content.UserRoleSetCallback) {
        if (event is Events.Content.UserRoleSetCallback.Success) {
            uiState.update { it.copy(dialog = DialogState.None, resultSetUserRole = ResultSetUserRoleState.Idle) }
            formRoleSelection = FormRoleSelectionState().validateSelection()
        }
    }
    private fun resetToLoadingState() {
        formRoleSelection = FormRoleSelectionState()
        uiState.update { it.copy(screenData = ScreenDataState.Loading, resultSetUserRole = ResultSetUserRoleState.Idle) }
    }
    private fun showSessionInvalidDialog(event: Events.Session.Invalid) {
        formRoleSelection = FormRoleSelectionState()
        uiState.update {
            it.copy(dialog = DialogState.SessionInvalid(event.error), resultSetUserRole = ResultSetUserRoleState.Idle)
        }
    }
    private fun loadSessionData(event: Events.Session.Valid) = viewModelScope.launch {
        val user = event.data.first
        val rolesFlow = getRolesFlow(user)
        combine(ucGetScreenData(), ucGetUserRoleCount(user)) { config, count -> config to count }
            .collect { (config, count) ->
                uiState.update {
                    it.copy(
                        screenData = ScreenDataState.Loaded(
                            confCommon = config,
                            sessionEvent = event.ev,
                            userData = user,
                            sessionData = event.data.third,
                            userRolesCount = count,
                            roles = rolesFlow
                        ),
                        resultSetUserRole = ResultSetUserRoleState.Idle
                    )
                }
            }
    }
    private fun getRolesFlow(user: UserEntity) = ucGetUserRole(
        user = user,
        query = formRoleSelection.fldSearchQuery,
        sortOption = formRoleSelection.toSortOption(),
        filterOption = formRoleSelection.fldCurrentFilter
    )
    private fun confirmRoleSelection(role : RoleEntity) = viewModelScope.launch {
        val loaded = uiState.value.screenData as? ScreenDataState.Loaded ?: return@launch
        if(uiState.value.resultSetUserRole == ResultSetUserRoleState.Loading) return@launch
        updateForm { it.setValue(selectedRole = role) }
        formRoleSelection = formRoleSelection.disableInputs()
        val user = loaded.userData ?: return@launch handleSetRoleFailure(Error.Input.MalformedError("User is not authorized to set role"))
        val session = loaded.sessionData ?: return@launch handleSetRoleFailure(Error.Input.MalformedError("Your session is invalid / expired, please re-log"))
        uiState.update { it.copy(resultSetUserRole = ResultSetUserRoleState.Loading) }
        val dto = DTORoleSelection(user = user, session = session, role = role)
        ucSetUserRole(dto).fold(
            onSuccess = { result ->
                uiState.update { it.copy(resultSetUserRole = ResultSetUserRoleState.Success(result)) }
                formRoleSelection = FormRoleSelectionState().validateSelection()
            },
            onFailure = {
                handleSetRoleFailure(it as? Error ?: Error.UnknownError(message = it.message, cause = it.cause))
            }
        )
    }
    private fun handleSetRoleFailure(error: Error) {
        uiState.update { it.copy(resultSetUserRole = ResultSetUserRoleState.Failure(error)) }
        formRoleSelection = FormRoleSelectionState().validateSelection()
    }
    private fun updateSelectedRole(role: RoleEntity) =
        updateForm { it.setValue(selectedRole = role).validateSelection() }
    private fun handleSearchQueryChange(query: String) {
        updateForm { it.setValue(searchQuery = query, selectedRole = null) }
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(debounceDelay)
            val loaded = uiState.value.screenData as? ScreenDataState.Loaded ?: return@launch
            val rolesFlow = getRolesFlow(loaded.userData ?: return@launch)
            uiState.update { it.copy(screenData = loaded.copy(roles = rolesFlow)) }
        }
    }
    private fun updateLayoutMode(mode: FormRoleSelectionState.Companion.LayoutMode) =
        updateForm { it.setValue(layoutMode = mode) }
    private fun updateForm(transform: (FormRoleSelectionState) -> FormRoleSelectionState) =
        viewModelScope.launch(Dispatchers.Main.immediate) {
            val updated = transform(formRoleSelection)
            formRoleSelection.takeIf { it != updated }?.let { formRoleSelection = updated }
        }
    private fun updateDialog(transform: (DialogState) -> DialogState) =
        uiState.update { it.copy(dialog = transform(it.dialog)) }
    private fun FormRoleSelectionState.toSortOption(): SortOption = when (fldCurrentSort) {
        FormRoleSelectionState.Companion.SortOption.LabelAsc -> SortOption.RoleLabelAsc
        FormRoleSelectionState.Companion.SortOption.LabelDesc -> SortOption.RoleLabelDesc
        FormRoleSelectionState.Companion.SortOption.CodeAsc -> SortOption.RoleCodeAsc
        FormRoleSelectionState.Companion.SortOption.CodeDesc -> SortOption.RoleCodeDesc
        FormRoleSelectionState.Companion.SortOption.TypeAsc -> SortOption.RoleTypeAsc
        FormRoleSelectionState.Companion.SortOption.TypeDesc -> SortOption.RoleTypeDesc
    }
}