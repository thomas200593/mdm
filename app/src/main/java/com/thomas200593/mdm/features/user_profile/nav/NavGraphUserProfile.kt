package com.thomas200593.mdm.features.user_profile.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.user_profile.ui.ScrUserProfile

fun NavGraphBuilder.navGraphUserProfile() {
    navigation <NavigationGraph.GraphUserProfile> (
        startDestination = ScrGraphs.DestTopLevel.Menu,
        builder = {
            composable<ScrGraphs.DestTopLevel.Menu>(
                content = { ScrUserProfile() }
            )
        }
    )
}
fun NavController.navToUserProfile() {}