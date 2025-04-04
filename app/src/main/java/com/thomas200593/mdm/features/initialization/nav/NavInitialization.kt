package com.thomas200593.mdm.features.initialization.nav

import androidx.navigation.NavController
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs

/**
 * Navigates to the Initialization screen.
 *
 * This function helps in navigating to the Initialization screen while ensuring that:
 * - The destination is launched as a single top instance to prevent duplicate instances.
 * - The previous navigation state is restored if available.
 * - The navigation stack is cleared up to [NavigationGraph.GraphInitial] inclusively.
 *
 * @receiver The [NavController] used for navigation.
 */
fun NavController.navToInitialization() = this.navigate(
    route = ScrGraphs.Initialization(),
    builder = {
        launchSingleTop = true // Ensures only a single instance of the screen exists.
        restoreState = true // Restores the previous navigation state if applicable.
        popUpTo(NavigationGraph.GraphInitial, popUpToBuilder = { inclusive = true })
        // Clears the navigation stack up to GraphInitial.
    }
)