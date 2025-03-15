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
import com.thomas200593.mdm.features.initial.entity.Initial
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
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMInitial.Ui.Events.OnOpenEvent) })
    ScrInitial(
        scrGraph = scrGraph,
        dataState = uiState.dataState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } }
    )
}

@Composable
private fun ScrInitial(
    scrGraph: ScrGraphs.Initial,
    dataState: VMInitial.Ui.DataState,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit
) = when (dataState) {
    VMInitial.Ui.DataState.Loading -> ScrLoading(loadingLabel = scrGraph.title)
    is VMInitial.Ui.DataState.Loaded -> ScreenContent(
        data = dataState.data,
        onNavToOnboarding = onNavToOnboarding,
        onNavToInitialization = onNavToInitialization,
        onNavToAuth = onNavToAuth
    )
}

@Composable
private fun ScreenContent(
    data: Initial,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit
) = when (data.confCommon.onboardingStatus) {
    OnboardingStatus.SHOW -> onNavToOnboarding()
    OnboardingStatus.HIDE -> when (data.confCommon.firstTimeStatus) {
        FirstTimeStatus.YES -> onNavToInitialization()
        FirstTimeStatus.NO -> onNavToAuth()
    }
}