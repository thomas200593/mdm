package com.thomas200593.mdm.core.design_system.state_app

import androidx.compose.runtime.staticCompositionLocalOf

val LocalStateApp = staticCompositionLocalOf<StateApp>
{ error(message = "${StateApp::class.simpleName} Not Provided!") }