package com.thomas200593.mdm.app.main.nav

import kotlin.reflect.KClass

enum class DestTopLevel(
    val scrGraphs: ScrGraphs.DestTopLevel,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        scrGraphs = ScrGraphs.DestTopLevel.Home,
        route = ScrGraphs.DestTopLevel.Home::class
    ),
    MENU(
        scrGraphs = ScrGraphs.DestTopLevel.Menu,
        route = ScrGraphs.DestTopLevel.Menu::class
    )
}