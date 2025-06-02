package com.thomas200593.mdm.features.tld.home.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.tld.home.ui.ScrHome

fun NavGraphBuilder.navGraphHome() {
    navigation <NavigationGraph.GraphHome> (
        startDestination = ScrGraphs.DestTopLevel.Home,
        builder = {
            composable<ScrGraphs.DestTopLevel.Home>(
                content = {
                    ScrHome(scrGraph = ScrGraphs.DestTopLevel.Home)
                }
            )
        }
    )
}
fun NavController.navToHome(navOptions: NavOptions) =
    navigate(route = NavigationGraph.GraphHome, navOptions = navOptions)