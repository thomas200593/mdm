package com.thomas200593.mdm.features.initial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.session.entity.SessionState
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.auth.nav.navToAuth
import com.thomas200593.mdm.features.initial.ui.events.Events
import com.thomas200593.mdm.features.initial.ui.state.ComponentsState
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.onboarding.nav.navToOnboarding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    scrGraph: ScrGraphs.Initial, vm: VMInitial = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrInitial(
        scrGraph = scrGraph, componentsState = uiState.componentsState, sessionState = stateApp.isSessionValid,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } }
    )
}
@Composable
private fun ScrInitial(
    scrGraph: ScrGraphs.Initial, componentsState: ComponentsState, sessionState: StateFlow<SessionState>,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit, onNavToAuth: () -> Unit
) = when (componentsState) {
    is ComponentsState.Loading -> ScrLoading(label = scrGraph.title)
    is ComponentsState.Loaded -> ScreenContent(
        components = componentsState, sessionState = sessionState,
        onNavToOnboarding = onNavToOnboarding, onNavToInitialization = onNavToInitialization, onNavToAuth = onNavToAuth
    )
}
@Composable
private fun ScreenContent(
    components: ComponentsState.Loaded, sessionState: StateFlow<SessionState>,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit, onNavToAuth: () -> Unit
) = when (components.confCommon.onboardingStatus) {
    OnboardingStatus.SHOW -> onNavToOnboarding()
    OnboardingStatus.HIDE -> when (components.confCommon.firstTimeStatus) {
        FirstTimeStatus.YES -> onNavToInitialization()
        FirstTimeStatus.NO -> {
            val session = sessionState.collectAsStateWithLifecycle().value
            LaunchedEffect(session) {
                when(session) {
                    is SessionState.Loading -> Unit
                    is SessionState.Invalid -> onNavToAuth()
                    is SessionState.Valid -> {
                        /** TODO
                         *  get current user session role,
                         *      if role is not decided to role selection
                         *      otherwise to dashboard with respected role
                         **/}
                }
            }
        }
    }
}