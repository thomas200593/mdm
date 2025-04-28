package com.thomas200593.mdm.features.role_selection.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mdm.app.main.nav.NavigationGraph
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.features.role_selection.ui.ScrRoleSelection

fun NavGraphBuilder.navGraphRoleSelection() {
    navigation <NavigationGraph.GraphRoleSelection>(
        startDestination = ScrGraphs.RoleSelection(),
        builder = {
            composable<ScrGraphs.RoleSelection>(content = { ScrRoleSelection(scrGraph = ScrGraphs.RoleSelection()) })
        }
    )
}