package com.thomas200593.mdm.features.security.auth.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.security.auth.ui.ScrAuth

fun NavGraphBuilder.navGraphAuth() {
    navigation<NavigationGraph.GraphAuth>(
        startDestination = ScrGraphs.Auth(),
        builder = {
            composable<ScrGraphs.Auth> { ScrAuth(scrGraph = ScrGraphs.Auth()) }
        }
    )
}
fun NavController.navToAuth() = this.navigate(
    route = NavigationGraph.GraphAuth,
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(route = NavigationGraph.GraphInitial, popUpToBuilder = { inclusive = true })
    }
)