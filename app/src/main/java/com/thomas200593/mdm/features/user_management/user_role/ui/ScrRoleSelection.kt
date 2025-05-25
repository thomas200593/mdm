package com.thomas200593.mdm.features.user_management.user_role.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.user_management.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ScreenDataState
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    stateApp.SessionHandler(
        onLoading = { ev -> vm.onSessionEvent(event = Events.Session.Loading(ev = ev)) },
        onInvalid = { ev, err -> vm.onSessionEvent(event = Events.Session.Invalid(ev = ev, error = err)) },
        onNoCurrentRole = { ev, data -> vm.onSessionEvent(event = Events.Session.NoCurrentRole(ev = ev, data = data)) },
        onValid = { ev, data, currentRole -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data, currentRole = currentRole)) }
    )
    ScrRoleSelection(uiState = uiState)
}
@Composable private fun ScrRoleSelection(
    uiState: VMRoleSelection.UiState
) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> {/*TODO*/}
}