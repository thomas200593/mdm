package com.thomas200593.mdm.app.main.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScrGraphs {
    @Serializable data object Dashboard: ScrGraphs
    @Serializable data object UserProfile: ScrGraphs
}