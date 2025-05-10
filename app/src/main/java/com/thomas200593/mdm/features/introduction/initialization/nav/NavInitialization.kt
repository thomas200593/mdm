package com.thomas200593.mdm.features.introduction.initialization.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.introduction.initialization.ui_2.ScrInitialization

fun NavGraphBuilder.navGraphInitialization() {
    navigation <NavigationGraph.GraphInitialization>(
        startDestination = ScrGraphs.Initialization(),
        builder = { composable<ScrGraphs.Initialization>(content = { ScrInitialization(scrGraph = ScrGraphs.Initialization()) }) }
    )
}
fun NavController.navToInitialization() = this.navigate(
    route = ScrGraphs.Initialization(),
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(NavigationGraph.GraphBootstrap, popUpToBuilder = { inclusive = true })
    }
)