package com.thomas200593.mdm.features.tld.user_profile.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.tld.user_profile.ui.ScrUserProfile

fun NavGraphBuilder.navGraphUserProfile() {
    navigation <NavigationGraph.GraphUserProfile> (
        startDestination = ScrGraphs.DestTopLevel.UserProfile,
        builder = {
            composable<ScrGraphs.DestTopLevel.UserProfile>(
                content = {
                    ScrUserProfile(scrGraph = ScrGraphs.DestTopLevel.UserProfile)
                }
            )
        }
    )
}
fun NavController.navToUserProfile(navOptions: NavOptions) =
    navigate(route = NavigationGraph.GraphUserProfile, navOptions = navOptions)