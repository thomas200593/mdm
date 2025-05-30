package com.thomas200593.mdm.features.tld.dashboard.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.tld.dashboard.ui.ScrDashboard

fun NavGraphBuilder.navGraphDashboard() {
    navigation <NavigationGraph.GraphDashboard> (
        startDestination = ScrGraphs.DestTopLevel.Dashboard,
        builder = {
            composable<ScrGraphs.DestTopLevel.Dashboard>(
                content = {
                    ScrDashboard(scrGraph = ScrGraphs.DestTopLevel.Dashboard)
                }
            )
        }
    )
}
fun NavController.navToDashboard(navOptions: NavOptions) =
    navigate(route = NavigationGraph.GraphDashboard, navOptions = navOptions)