package com.thomas200593.mdm.core.design_system.state_app

import android.util.Log
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
import com.thomas200593.mdm.core.design_system.session.SessionManager
import com.thomas200593.mdm.core.design_system.session.entity.SessionState
import com.thomas200593.mdm.core.design_system.timber_logger.LocalTimberFileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val TAG = StateApp::class.simpleName

@Composable
fun rememberStateApp(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    networkMonitor: NetworkMonitor,
    timberFileLogger: TimberFileLogger = LocalTimberFileLogger.current,
    sessionManager: SessionManager
): StateApp = remember(
    coroutineScope,
    navController,
    networkMonitor,
    timberFileLogger,
    sessionManager
) {
    StateApp(
        coroutineScope = coroutineScope,
        navController = navController,
        networkMonitor = networkMonitor,
        timberFileLogger = timberFileLogger,
        sessionManager = sessionManager
    )
}
@Stable
class StateApp(
    coroutineScope: CoroutineScope,
    val navController: NavHostController,
    networkMonitor: NetworkMonitor,
    val timberFileLogger: TimberFileLogger,
    sessionManager: SessionManager
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline.map(Boolean::not)
        .stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(1_000), initialValue = false)
    val isSessionValid = sessionManager.currentSession
        .stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = SessionState.Loading)
    val destTopLevel: List<DestTopLevel> = DestTopLevel.entries
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val currentDestination: NavDestination? @Composable get() =
        navController.currentBackStackEntryFlow.collectAsState(initial = null).value?.destination
            .also { if (it != null) previousDestination.value = it } ?: previousDestination.value
    val currentTopLevelDestination: DestTopLevel? @Composable get() =
        DestTopLevel.entries.firstOrNull { currentDestination?.hasRoute(route = it.route) == true }
    fun navToDestTopLevel(dest: DestTopLevel) {
        val topLevelDestNavOptions = navOptions(optionsBuilder = {
            popUpTo(id = navController.graph.findStartDestination().id, popUpToBuilder = { saveState = true })
            launchSingleTop = true; restoreState = true
        })
        timberFileLogger.log(Log.DEBUG, TAG, "${this@StateApp::class.simpleName}.navToDestTopLevel()")
        when (dest) {
            DestTopLevel.DASHBOARD -> {
                timberFileLogger.log(Log.DEBUG, TAG, "")
            }
            DestTopLevel.USER_PROFILE -> {
                timberFileLogger.log(Log.DEBUG, TAG, "")
            }
        }
    }
}