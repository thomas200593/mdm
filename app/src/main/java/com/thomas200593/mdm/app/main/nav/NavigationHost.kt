package com.thomas200593.mdm.app.main.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.thomas200593.mdm.core.design_system.state_app.StateApp

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    stateApp: StateApp,
    onShowSnackBar: suspend (String, String?) -> Boolean
) {
    val navController = stateApp.navController
    NavHost(
        navController = navController,
        startDestination = "",
        modifier = modifier
    ) {
        /*TODO*/
    }
}