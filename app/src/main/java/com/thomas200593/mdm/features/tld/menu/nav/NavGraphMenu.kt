package com.thomas200593.mdm.features.tld.menu.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.tld.menu.ui.ScrMenu

fun NavGraphBuilder.navGraphMenu() {
    navigation <NavigationGraph.GraphMenu> (
        startDestination = ScrGraphs.DestTopLevel.Menu,
        builder = {
            composable<ScrGraphs.DestTopLevel.Menu>(
                content = {
                    ScrMenu(scrGraph = ScrGraphs.DestTopLevel.Menu)
                }
            )
        }
    )
}
fun NavController.navToMenu(navOptions: NavOptions) =
    navigate(route = NavigationGraph.GraphMenu, navOptions = navOptions)