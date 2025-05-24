package com.thomas200593.mdm.features.bootstrap.ui

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
import com.thomas200593.mdm.features.bootstrap.ui.events.Events
import com.thomas200593.mdm.features.bootstrap.ui.state.ScreenDataState
import com.thomas200593.mdm.features.user_management.security.auth.nav.navToAuth
import com.thomas200593.mdm.features.introduction.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.introduction.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.introduction.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.introduction.onboarding.nav.navToOnboarding
import com.thomas200593.mdm.features.user_management.user_role.nav.navToRoleSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable fun ScrBootstrap(
    scrGraph: ScrGraphs.Bootstrap, vm: VMBootstrap = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrBootstrap(
        scrGraph = scrGraph, screenData = uiState.screenData,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } },
        onNavToRoleSelection = { coroutineScope.launch { stateApp.navController.navToRoleSelection() } },
        onNavToDashboard = {  }
    )
}
@Composable private fun ScrBootstrap(
    scrGraph: ScrGraphs.Bootstrap, screenData: ScreenDataState,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit, onNavToAuth: () -> Unit,
    onNavToRoleSelection: () -> Unit, onNavToDashboard: () -> Unit
) = when (screenData) {
    is ScreenDataState.Loading -> ScrLoading(label = scrGraph.title)
    is ScreenDataState.Loaded -> ScreenContent(
        components = screenData,
        onNavToOnboarding = onNavToOnboarding, onNavToInitialization = onNavToInitialization,
        onNavToAuth = onNavToAuth, onNavToRoleSelection = onNavToRoleSelection, onNavToDashboard = onNavToDashboard
    )
}
@Composable private fun ScreenContent(
    components: ScreenDataState.Loaded, stateApp: StateApp = LocalStateApp.current,
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
                onValid = { _, _, _ -> onNavToDashboard() }
            )
        }
    }
}