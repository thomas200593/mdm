package com.thomas200593.mdm.features.initial.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.auth.nav.navGraphAuth
import com.thomas200593.mdm.features.initial.ui.ScrInitial
import com.thomas200593.mdm.features.onboarding.nav.navGraphOnboarding

fun NavGraphBuilder.navGraphInitial() {
    navigation<NavigationGraph.GraphInitial>(
        startDestination = ScrGraphs.Initial,
        builder = {
            composable<ScrGraphs.Initial>(content = { ScrInitial() })
            navGraphOnboarding()
            navGraphAuth()
        }
    )
}