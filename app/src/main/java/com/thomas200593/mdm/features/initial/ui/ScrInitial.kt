package com.thomas200593.mdm.features.initial.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.timber_logger.LocalTimberFileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.auth.nav.navToAuth
import com.thomas200593.mdm.features.initial.ui.events.Events
import com.thomas200593.mdm.features.initial.ui.state.ComponentsState
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.onboarding.nav.navToOnboarding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "ScrInitial"

@Composable
fun ScrInitial(
    scrGraph: ScrGraphs.Initial, vm: VMInitial = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    fileLogger.log(Log.DEBUG, TAG, "ScrInitial()")
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrInitial(
        scrGraph = scrGraph, componentsState = uiState.componentsState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } }
    )
}
@Composable
private fun ScrInitial(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, scrGraph: ScrGraphs.Initial,
    componentsState: ComponentsState, onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit
) = when (componentsState) {
    is ComponentsState.Loading -> {
        fileLogger.log(Log.DEBUG, TAG, componentsState.toString())
        ScrLoading(label = scrGraph.title)
    }
    is ComponentsState.Loaded -> {
        fileLogger.log(Log.DEBUG, TAG, componentsState.toString())
        ScreenContent(
            components = componentsState,
            onNavToOnboarding = onNavToOnboarding,
            onNavToInitialization = onNavToInitialization,
            onNavToAuth = onNavToAuth
        )
    }
}
@Composable
private fun ScreenContent(
    components: ComponentsState.Loaded,
    onNavToOnboarding: () -> Unit, onNavToInitialization: () -> Unit, onNavToAuth: () -> Unit
) = when (components.confCommon.onboardingStatus) {
    OnboardingStatus.SHOW -> onNavToOnboarding()
    OnboardingStatus.HIDE -> when (components.confCommon.firstTimeStatus) {
        FirstTimeStatus.YES -> onNavToInitialization()
        FirstTimeStatus.NO -> onNavToAuth()
    }
}