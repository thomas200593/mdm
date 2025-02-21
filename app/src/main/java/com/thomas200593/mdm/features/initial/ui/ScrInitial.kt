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
import com.thomas200593.mdm.features.initial.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initial.entity.Initial
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus
import kotlinx.coroutines.CoroutineScope

@Composable
fun ScrInitial(
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current,
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.onEvent(VMInitial.Ui.Events.OnOpenEvent) }
    ScrInitial(state = state.state)
}

@Composable
private fun ScrInitial(state: VMInitial.Ui.State) = when(state) {
    VMInitial.Ui.State.Loading -> {/*TODO*/}
    is VMInitial.Ui.State.Error -> {/*TODO*/}
    is VMInitial.Ui.State.Success -> ScreenContent(
        data = state.data,
        onNavToOnboarding = {/*TODO*/},
        onNavToInitialization = {/*TODO*/}
    )
}

@Composable
private fun ScreenContent(
    data : Initial,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit
) = when (data.confCommon.firstTimeStatus) {
    FirstTimeStatus.YES -> when (data.confCommon.onboardingStatus) {
        OnboardingStatus.SHOW -> onNavToOnboarding()
        OnboardingStatus.HIDE -> onNavToInitialization()
    }
    FirstTimeStatus.NO -> when (data.confCommon.onboardingStatus) {
        OnboardingStatus.SHOW -> onNavToOnboarding()
        OnboardingStatus.HIDE -> {/*TODO*/} //Auth
    }
}