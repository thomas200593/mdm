package com.thomas200593.mdm.core.design_system.state_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.thomas200593.mdm.app.main.nav.DestTopLevel
import com.thomas200593.mdm.core.design_system.network_monitor.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberStateApp(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    networkMonitor: NetworkMonitor
) : StateApp = remember(
    coroutineScope,
    navController,
    networkMonitor
) {
    StateApp(
        coroutineScope = coroutineScope,
        navController = navController,
        networkMonitor = networkMonitor
    )
}

@Stable
class StateApp(
    coroutineScope: CoroutineScope,
    val navController: NavHostController,
    networkMonitor: NetworkMonitor
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline.map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = false
        )
    val destTopLevel: List<DestTopLevel> = DestTopLevel.entries
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow.collectAsState(null)
            return currentEntry.value?.destination.also {
                if(it != null) previousDestination.value = it
            } ?: previousDestination.value
        }
    val currentTopLevelDestination: DestTopLevel?
        @Composable get() = DestTopLevel.entries.firstOrNull {
            currentDestination?.hasRoute(route = it.route) == true
        }
    fun navToDestTopLevel(dest: DestTopLevel) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true; restoreState = true
        }
        when(dest) {
            DestTopLevel.DASHBOARD -> {/*TODO*/}
            DestTopLevel.USER_PROFILE -> {/*TODO*/}
        }
    }
}