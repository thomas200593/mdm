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
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.thomas200593.mdm.core.ui.component.top_bar.DestTopLevelAppBar
import kotlin.reflect.KClass

@Composable fun ScrApp(
    stateApp: StateApp = LocalStateApp.current,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isNetworkOffline by stateApp.isNetworkOffline.collectAsStateWithLifecycle()
    val strNetworkOffline = stringResource(R.string.str_network_offline)
    LaunchedEffect(
        key1 = isNetworkOffline,
        block = { if (isNetworkOffline) snackBarHostState.showSnackbar(message = strNetworkOffline, duration = SnackbarDuration.Indefinite) }
    )
    ScrApp(
        stateApp = stateApp,
        windowAdaptiveInfo = windowAdaptiveInfo,
        snackBarHostState = snackBarHostState
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun ScrApp(
    modifier: Modifier = Modifier,
    stateApp: StateApp,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    snackBarHostState: SnackbarHostState,
) {
    val currentDestination = stateApp.currentDestination
    val isTld = stateApp.destTopLevel.any { dest -> currentDestination.isRouteInHierarchy(dest.baseRoute) }
    val navSuiteScaffoldState = rememberNavigationSuiteScaffoldState()
    LaunchedEffect(isTld) {
        if (isTld) navSuiteScaffoldState.show()
        else navSuiteScaffoldState.hide()
    }
    AppNavSuiteScaffold(
        modifier = modifier,
        state = navSuiteScaffoldState,
        navSuiteItems = {
            stateApp.destTopLevel.forEach { dest ->
                val selected = currentDestination.isRouteInHierarchy(dest.baseRoute)
                item(
                    selected = selected,
                    onClick = { stateApp.navToDestTopLevel(dest) },
                    icon = {
                        Icon(
                            imageVector = AppIcons.mapTopLevelToMaterialIcons(dest.scrGraphs.iconRes),
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = AppIcons.mapTopLevelToMaterialIcons(dest.scrGraphs.iconRes),
                            contentDescription = null
                        )
                    },
                    label = { dest.scrGraphs.title.let { Text(stringResource(it)) } },
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
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(it).consumeWindowInsets(it)
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
                ) {
                    val destination = stateApp.currentTopLevelDestination
                    var shouldShowTopAppBar = false
                    if (destination != null) {
                        shouldShowTopAppBar = true
                        DestTopLevelAppBar(
                            modifier = modifier,
                            navigationIcon = destination.scrGraphs.navigationIcon,
                            title = destination.scrGraphs.title,
                            actions = destination.scrGraphs.actions
                        )
                    }
                    Box(
                        modifier = Modifier.consumeWindowInsets(
                            if (shouldShowTopAppBar) WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                            else WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
                        ),
                        content = {
                            NavigationHost(
                                stateApp = stateApp,
                                onShowSnackBar = { message, action ->
                                    snackBarHostState.showSnackbar(
                                        message = message,
                                        actionLabel = action,
                                        duration = SnackbarDuration.Short
                                    ) == SnackbarResult.ActionPerformed
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}
private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any { it.hasRoute(route) } == true