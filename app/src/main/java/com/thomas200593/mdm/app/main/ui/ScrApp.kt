package com.thomas200593.mdm.app.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.NavigationHost
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.common.AppIcons
import com.thomas200593.mdm.core.ui.component.AppNavSuiteScaffold
import com.thomas200593.mdm.core.ui.component.DestTopLevelAppBar
import kotlin.reflect.KClass

@Composable
fun ScrApp(
    stateApp: StateApp = LocalStateApp.current,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isNetworkOffline by stateApp.isNetworkOffline.collectAsStateWithLifecycle()
    val strNetworkOffline = stringResource(R.string.str_network_offline)

    LaunchedEffect(isNetworkOffline) {
        if(isNetworkOffline) snackBarHostState
            .showSnackbar(message = strNetworkOffline, duration = SnackbarDuration.Indefinite)
    }

    ScrApp(
        stateApp = stateApp,
        windowAdaptiveInfo = windowAdaptiveInfo,
        snackBarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScrApp(
    modifier: Modifier = Modifier,
    stateApp: StateApp,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    snackBarHostState: SnackbarHostState,
) {
    val currentDestination = stateApp.currentDestination
    AppNavSuiteScaffold(
        modifier = modifier,
        navSuiteItems = {
            stateApp.destTopLevel.forEach { dest ->
                val selected = currentDestination.isRouteInHierarchy(dest.baseRoute)
                item(
                    selected = selected,
                    onClick = { stateApp.navToDestTopLevel(dest) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(dest.scrGraphs.iconRes),
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(dest.scrGraphs.iconRes),
                            contentDescription = null
                        )
                    },
                    label = { dest.scrGraphs.title?.let { Text(stringResource(it)) } },
                    modifier = Modifier
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        content = {
            Scaffold(
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                        )
                ) {
                    val destination = stateApp.currentTopLevelDestination
                    var shouldShowTopAppBar = false

                    if(destination != null) {
                        shouldShowTopAppBar = true
                        DestTopLevelAppBar(
                            title = destination.scrGraphs.title,
                            navBtnIcon = destination.scrGraphs.iconRes,
                            modifier = modifier,
                            colors = TopAppBarDefaults.topAppBarColors(),
                            navBtnOnClick = {},
                            actBtnIcon = AppIcons.App.icon,
                            actBtnOnClick = {}
                        )
                    }

                    Box(
                        modifier = Modifier.consumeWindowInsets(
                            if(shouldShowTopAppBar) WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                            else WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
                        ),
                        content = {
                            NavigationHost(
                                stateApp = stateApp
                            )
                        }
                    )
                }
            }
        }
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) = this?.hierarchy
    ?.any { it.hasRoute(route) } ?: false