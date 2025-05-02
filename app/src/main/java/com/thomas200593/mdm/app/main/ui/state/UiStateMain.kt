package com.thomas200593.mdm.app.main.ui.state

import com.thomas200593.mdm.features.cnf_ui_contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.cnf_ui_dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.cnf_ui_font_size.entity.FontSize
import com.thomas200593.mdm.features.cnf_ui_theme.entity.Theme
import com.thomas200593.mdm.features.cnf_common.entity.Common

sealed interface UiStateMain {
    data object Loading : UiStateMain
    data class Loaded(val confCommon: Common) : UiStateMain {
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
        override val fontSize: FontSize = when (confCommon.ui.fontSize) {
            FontSize.SMALL -> FontSize.SMALL
            FontSize.MEDIUM -> FontSize.MEDIUM
            FontSize.LARGE -> FontSize.LARGE
            FontSize.EXTRA_LARGE -> FontSize.EXTRA_LARGE
        }
    }
    fun keepSplashScreenOn() = this is Loading
    fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
    val dynamicColorEnabled get() = false
    val contrastAccent get() = ContrastAccent.defaultValue
    val fontSize get() = FontSize.defaultValue
}