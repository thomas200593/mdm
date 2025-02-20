package com.thomas200593.mdm.app.main.nav

import kotlin.reflect.KClass

enum class DestTopLevel(
    val scrGraphs: ScrGraphs.DestTopLevel,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
){
    DASHBOARD(
        scrGraphs = ScrGraphs.DestTopLevel.Dashboard,
        route = ScrGraphs.DestTopLevel.Dashboard::class
    ),
    USER_PROFILE(
        scrGraphs = ScrGraphs.DestTopLevel.UserProfile,
        route = ScrGraphs.DestTopLevel.UserProfile::class
    )
}