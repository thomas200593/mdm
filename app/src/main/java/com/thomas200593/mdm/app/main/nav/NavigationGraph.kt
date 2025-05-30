package com.thomas200593.mdm.app.main.nav

import kotlinx.serialization.Serializable

@Serializable sealed interface NavigationGraph {
    @Serializable data object GraphRoot
    @Serializable data object GraphBootstrap
    @Serializable data object GraphOnboarding
    @Serializable data object GraphInitialization
    @Serializable data object GraphAuth
    @Serializable data object GraphRoleSelection
    @Serializable data object GraphDashboard
}