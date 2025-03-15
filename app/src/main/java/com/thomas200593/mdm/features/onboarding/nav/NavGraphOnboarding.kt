package com.thomas200593.mdm.features.onboarding.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.onboarding.ui.ScrOnboarding

fun NavGraphBuilder.navGraphOnboarding() {
    navigation<NavigationGraph.GraphOnboarding>(
        startDestination = ScrGraphs.Onboarding(),
        builder = {
            composable<ScrGraphs.Onboarding>(content = { ScrOnboarding(scrGraph = ScrGraphs.Onboarding()) })
        }
    )
}

fun NavController.navToOnboarding() = this.navigate(
    route = NavigationGraph.GraphOnboarding,
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(route = NavigationGraph.GraphInitial, popUpToBuilder = { inclusive = true })
    }
)