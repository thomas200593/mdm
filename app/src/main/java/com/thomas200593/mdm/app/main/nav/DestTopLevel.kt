package com.thomas200593.mdm.app.main.nav

import kotlin.reflect.KClass

enum class DestTopLevel(
    val scrGraphs: ScrGraphs,
    route: KClass<*>,
    baseRoute: KClass<*> = route
) {
    DASHBOARD(
        scrGraphs = ScrGraphs.DestTopLevel.Dashboard,
        route = ScrGraphs.DestTopLevel.Dashboard::class,
        baseRoute = ScrGraphs.DestTopLevel.Dashboard::class
    ),
    PROFILE(
        scrGraphs = ScrGraphs.DestTopLevel.UserProfile,
        route = ScrGraphs.DestTopLevel.UserProfile::class
    )
}