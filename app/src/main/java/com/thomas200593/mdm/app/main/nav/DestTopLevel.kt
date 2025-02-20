package com.thomas200593.mdm.app.main.nav

import kotlin.reflect.KClass

enum class DestTopLevel(
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
){
    DASHBOARD(route = ScrGraphs.Dashboard::class),
    USER_PROFILE(route = ScrGraphs.UserProfile::class)
}