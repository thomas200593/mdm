package com.thomas200593.mdm.features.onboarding.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs

fun NavGraphBuilder.navGraphOnboarding() {
    navigation<NavigationGraph.GraphOnboarding>(
        startDestination = ScrGraphs.Onboarding,
        builder = {
            composable<ScrGraphs.Onboarding>(content = { /*TODO*/ })
        }
    )
}