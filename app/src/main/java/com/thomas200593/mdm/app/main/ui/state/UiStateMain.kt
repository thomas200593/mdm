package com.thomas200593.mdm.app.main.ui.state

import com.thomas200593.mdm.features.conf.__dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf.common.Common
import com.thomas200593.mdm.features.conf.__theme.Theme

sealed interface UiStateMain {
    data object Loading: UiStateMain
    data class Success(val confCommon: Common): UiStateMain {
        override fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = when (confCommon.ui.theme) {
            Theme.SYSTEM -> isSystemInDarkTheme
            Theme.LIGHT -> false
            Theme.DARK -> true
        }
        override val dynamicColorEnabled = when (confCommon.ui.dynamicColor) {
            DynamicColor.DISABLED -> false
            DynamicColor.ENABLED -> true
        }
    }
    fun keepSplashScreenOn() = this is Loading
    fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
    val dynamicColorEnabled get() = false
}