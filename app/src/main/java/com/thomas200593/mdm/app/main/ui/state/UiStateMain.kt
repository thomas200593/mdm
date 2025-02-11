package com.thomas200593.mdm.app.main.ui.state

import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.conf.__theme.entity.Theme
import com.thomas200593.mdm.features.conf.common.entity.Common

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
        override val contrastAccent: ContrastAccent = when (confCommon.ui.contrastAccent) {
            ContrastAccent.DEFAULT -> ContrastAccent.DEFAULT
            ContrastAccent.MEDIUM -> ContrastAccent.MEDIUM
            ContrastAccent.HIGH -> ContrastAccent.HIGH
        }
    }
    fun keepSplashScreenOn() = this is Loading
    fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
    val dynamicColorEnabled get() = false
    val contrastAccent get() = ContrastAccent.defaultValue
}