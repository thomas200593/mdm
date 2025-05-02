package com.thomas200593.mdm.features.initial.initial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.initial.initial.ui.events.Events
import com.thomas200593.mdm.features.initial.initial.ui.state.ComponentsState
import com.thomas200593.mdm.features.security.auth.nav.navToAuth
import com.thomas200593.mdm.features.user.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.user.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.initial.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.initial.onboarding.nav.navToOnboarding
import com.thomas200593.mdm.features.user.user_role.nav.navToRoleSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable fun ScrInitial(
    scrGraph: ScrGraphs.Initial, vm: VMInitial = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrInitial(
        scrGraph = scrGraph, componentsState = uiState.componentsState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } },
        onNavToRoleSelection = { coroutineScope.launch { stateApp.navController.navToRoleSelection() } },
        onNavToDashboard = {  }
    )
}
@Composable private fun ScrInitial(
    scrGraph: ScrGraphs.Initial, componentsState: ComponentsState,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit, onNavToAuth: () -> Unit,
    onNavToRoleSelection: () -> Unit, onNavToDashboard: () -> Unit
) = when (componentsState) {
    is ComponentsState.Loading -> ScrLoading(label = scrGraph.title)
    is ComponentsState.Loaded -> ScreenContent(
        components = componentsState,
        onNavToOnboarding = onNavToOnboarding, onNavToInitialization = onNavToInitialization,
        onNavToAuth = onNavToAuth, onNavToRoleSelection = onNavToRoleSelection, onNavToDashboard = onNavToDashboard
    )
}
@Composable private fun ScreenContent(
    components: ComponentsState.Loaded, stateApp: StateApp = LocalStateApp.current,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit, onNavToRoleSelection: () -> Unit, onNavToDashboard: () -> Unit
) = when (components.confCommon.onboardingStatus) {
    OnboardingStatus.SHOW -> onNavToOnboarding()
    OnboardingStatus.HIDE -> when (components.confCommon.firstTimeStatus) {
        FirstTimeStatus.YES -> onNavToInitialization()
        FirstTimeStatus.NO -> {
            stateApp.SessionHandler(
                onLoading = { _ -> },
                onInvalid = { _, _ -> onNavToAuth() },
                onNoCurrentRole = { _, _ -> onNavToRoleSelection() },
                onValid = { _, _ -> onNavToDashboard() }
            )
        }
    }
}