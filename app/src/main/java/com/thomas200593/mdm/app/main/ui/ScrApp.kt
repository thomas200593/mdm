package com.thomas200593.mdm.app.main.ui

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.AppNavSuiteScaffold

@Composable
fun ScrApp(
    stateApp: StateApp = LocalStateApp.current,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isNetworkOffline by stateApp.isNetworkOffline.collectAsStateWithLifecycle()
    val strNetworkOffline = stringResource(R.string.app_name)

    LaunchedEffect(isNetworkOffline) {
        if(isNetworkOffline) snackBarHostState.showSnackbar(
            message = strNetworkOffline,
            duration = SnackbarDuration.Indefinite
        )
    }

    ScrApp(
        stateApp = stateApp,
        windowAdaptiveInfo = windowAdaptiveInfo,
        snackBarHostState = snackBarHostState
    )
}

@Composable
private fun ScrApp(
    modifier: Modifier = Modifier,
    stateApp: StateApp,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    snackBarHostState: SnackbarHostState,
) {
    AppNavSuiteScaffold(
        modifier = modifier,
        navSuiteItems = {},
        windowAdaptiveInfo = windowAdaptiveInfo,
        content = { /*TODO*/ }
    )
}