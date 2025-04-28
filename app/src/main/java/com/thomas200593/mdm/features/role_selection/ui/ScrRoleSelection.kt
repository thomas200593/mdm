package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.role_selection.ui.state.ComponentsState
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    stateApp.SessionHandler { event, data, error ->
        when(event) {
            is SessionEvent.Invalid -> { /*vm.event.Screen.Session.Invalid.also.navigateToAuth*/ }
            is SessionEvent.Loading -> { /*vm.event.Screen.Session.LoadingEvent*/ }
            is SessionEvent.NoRole, is SessionEvent.Valid -> { /*vm.event.Screen.onOpenEvent*/ }
        }
    }
    when(uiState.componentsState) {
        is ComponentsState.Loading -> ScrLoading()
        is ComponentsState.Loaded -> ScrRoleSelection()
    }
}
@Composable private fun ScrRoleSelection() {

}