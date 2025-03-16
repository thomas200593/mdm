package com.thomas200593.mdm.features.initial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.features.auth.nav.navToAuth
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.onboarding.nav.navToOnboarding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    scrGraph: ScrGraphs.Initial,
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMInitial.Events.OnOpenEvent) })
    ScrInitial(
        scrGraph = scrGraph,
        scrDataState = uiState.scrDataState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } }
    )
}

@Composable
private fun ScrInitial(
    scrGraph: ScrGraphs.Initial,
    scrDataState: VMInitial.ScrDataState,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit
) = when (scrDataState) {
    VMInitial.ScrDataState.Loading -> ScrLoading(loadingLabel = scrGraph.title)
    is VMInitial.ScrDataState.Loaded -> ScreenContent(
        scrData = scrDataState.scrData,
        onNavToOnboarding = onNavToOnboarding,
        onNavToInitialization = onNavToInitialization,
        onNavToAuth = onNavToAuth
    )
}

@Composable
private fun ScreenContent(
    scrData: VMInitial.ScrData,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit
) = when (scrData.confCommon.onboardingStatus) {
    OnboardingStatus.SHOW -> onNavToOnboarding()
    OnboardingStatus.HIDE -> when (scrData.confCommon.firstTimeStatus) {
        FirstTimeStatus.YES -> onNavToInitialization()
        FirstTimeStatus.NO -> onNavToAuth()
    }
}