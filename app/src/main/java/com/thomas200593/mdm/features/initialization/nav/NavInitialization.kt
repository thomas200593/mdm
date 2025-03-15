package com.thomas200593.mdm.features.initialization.nav

import androidx.navigation.NavController
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs

fun NavController.navToInitialization() = this.navigate(
    route = ScrGraphs.Initialization(),
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(NavigationGraph.GraphInitial, popUpToBuilder = { inclusive = true })
    }
)