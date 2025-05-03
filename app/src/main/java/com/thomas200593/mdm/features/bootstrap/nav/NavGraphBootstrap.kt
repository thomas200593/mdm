package com.thomas200593.mdm.features.bootstrap.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.user_management.security.auth.nav.navGraphAuth
import com.thomas200593.mdm.features.bootstrap.ui.ScrBootstrap
import com.thomas200593.mdm.features.introduction.initialization.nav.navGraphInitialization
import com.thomas200593.mdm.features.introduction.onboarding.nav.navGraphOnboarding
import com.thomas200593.mdm.features.user_management.user_role.nav.navGraphRoleSelection

fun NavGraphBuilder.navGraphBootstrap() {
    navigation<NavigationGraph.GraphBootstrap>(
        startDestination = ScrGraphs.Bootstrap(),
        builder = {
            composable<ScrGraphs.Bootstrap>(content = { ScrBootstrap(scrGraph = ScrGraphs.Bootstrap()) })
            navGraphOnboarding()
            navGraphInitialization()
            navGraphAuth()
            navGraphRoleSelection()
        }
    )
}
fun NavController.navToBootstrap() = this.navigate(
    route = NavigationGraph.GraphBootstrap,
    builder = {
        launchSingleTop = true; restoreState = true
        popUpTo(route = NavigationGraph.GraphBootstrap, popUpToBuilder = { inclusive = true })
    }
)
