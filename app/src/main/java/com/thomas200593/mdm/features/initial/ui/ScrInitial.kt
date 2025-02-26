package com.thomas200593.mdm.features.initial.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.features.initial.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initial.entity.Initial
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mdm.features.onboarding.nav.navToOnboarding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current,
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMInitial.Ui.Events.OnOpenEvent) })
    ScrInitial(
        dataState = uiState.dataState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } }
    )
}

@Composable
private fun ScrInitial(
    dataState: VMInitial.Ui.DataState,
    onNavToOnboarding: () -> Unit
) = when (dataState) {
    VMInitial.Ui.DataState.Loading -> ScrLoading()
    is VMInitial.Ui.DataState.Error -> { /*TODO*/ }
    is VMInitial.Ui.DataState.Success -> ScreenContent(
        data = dataState.data,
        onNavToOnboarding = onNavToOnboarding,
        onNavToInitialization = { /*TODO*/ }
    )
}

@Composable
private fun ScreenContent(
    data: Initial,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit
) = when (data.confCommon.firstTimeStatus) {
    FirstTimeStatus.YES -> when (data.confCommon.onboardingStatus) {
        OnboardingStatus.SHOW -> onNavToOnboarding()
        OnboardingStatus.HIDE -> onNavToInitialization()
    }
    FirstTimeStatus.NO -> when (data.confCommon.onboardingStatus) {
        OnboardingStatus.SHOW -> onNavToOnboarding()
        OnboardingStatus.HIDE -> { /*TODO*/ } //Auth
    }
}