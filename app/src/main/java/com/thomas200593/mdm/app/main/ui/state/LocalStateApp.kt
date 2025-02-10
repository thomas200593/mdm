package com.thomas200593.mdm.app.main.ui.state

import androidx.compose.runtime.staticCompositionLocalOf

val LocalStateApp = staticCompositionLocalOf<StateApp> {
    error(message = "${StateApp::class.simpleName} Not Provided!")
}