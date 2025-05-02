package com.thomas200593.mdm.features.initial.initial.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.security.auth.nav.navGraphAuth
import com.thomas200593.mdm.features.initial.initial.ui.ScrInitial
import com.thomas200593.mdm.features.initial._initialization.nav.navGraphInitialization
import com.thomas200593.mdm.features.initial._onboarding.nav.navGraphOnboarding
import com.thomas200593.mdm.features.role_selection.nav.navGraphRoleSelection

fun NavGraphBuilder.navGraphInitial() {
    navigation<NavigationGraph.GraphInitial>(
        startDestination = ScrGraphs.Initial(),
        builder = {
            composable<ScrGraphs.Initial>(content = { ScrInitial(scrGraph = ScrGraphs.Initial()) })
            navGraphOnboarding()
            navGraphInitialization()
            navGraphAuth()
            navGraphRoleSelection()
        }
    )
}
fun NavController.navToInitial() = this.navigate(
    route = NavigationGraph.GraphInitial,
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(route = NavigationGraph.GraphInitial, popUpToBuilder = { inclusive = true })
    }
)
