package com.thomas200593.mdm.app.main.nav

import kotlinx.serialization.Serializable

@Serializable sealed interface NavigationGraph {
    @Serializable data object GraphRoot
    @Serializable data object GraphInitial
    @Serializable data object GraphOnboarding
}