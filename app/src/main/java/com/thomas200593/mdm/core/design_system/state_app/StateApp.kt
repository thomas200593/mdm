package com.thomas200593.mdm.core.design_system.state_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thomas200593.mdm.core.design_system.network_monitor.NetworkMonitor
import kotlinx.coroutines.CoroutineScope

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
    navController: NavHostController,
    networkMonitor: NetworkMonitor
)