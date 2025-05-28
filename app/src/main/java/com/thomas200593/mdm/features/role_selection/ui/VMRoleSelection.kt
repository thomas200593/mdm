package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType
import com.thomas200593.mdm.features.management.user_role.domain.UCGetUserRole
import com.thomas200593.mdm.features.management.user_role.entity.FilterOption
import com.thomas200593.mdm.features.management.user_role.entity.SortOption
import com.thomas200593.mdm.features.role_selection.domain.UCGetScreenData
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
        is Events.TopBar.BtnSignOut.Clicked -> {/*TODO*/}
    }
    fun onFormEvent(event: Events.Content.Form) = when (event) {
        is Events.Content.Form.SelectedRole -> handleRoleSelection(event.role)
        is Events.Content.Form.ModalBottomSheetSortFilter.Clicked -> {/*TODO*/}
        is Events.Content.Form.SearchBar.QueryChanged -> {
            updateForm { it.setValue(searchQuery = event.query, selectedRole = null) }
            handleSearchBarQueryField()
        }
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when (event) {
        is Events.BottomBar.BtnConfirmRole.Clicked -> {/*TODO*/}
        is Events.BottomBar.BtnRoleInfo.Clicked -> {/*TODO*/}
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
        updateForm { it.setValue(user = user) }
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
            filterOption = when (formRoleSelection.fldCurrentFilter) {
                RoleType.BuiltIn -> FilterOption.RoleTypeBuiltIn
                RoleType.UserDefined -> FilterOption.RoleTypeUserDefined
                null -> FilterOption.RoleTypeAll
            }
        )
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
    private fun handleRoleSelection(role: RoleEntity) {
        val loadedState = uiState.value.screenData as? ScreenDataState.Loaded ?: return
        val session = loadedState.sessionData ?: return
        updateForm { it.setValue(selectedRole = role).validateSelection(session) }
    }
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
            formRoleSelection.fldUser?.let { user ->
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
                    filterOption = when (formRoleSelection.fldCurrentFilter) {
                        RoleType.BuiltIn -> FilterOption.RoleTypeBuiltIn
                        RoleType.UserDefined -> FilterOption.RoleTypeUserDefined
                        null -> FilterOption.RoleTypeAll
                    }
                )
                uiState.update {
                    it.copy(screenData = currentSession.copy(roles = rolesFlow))
                }
            }
        }
    }
}