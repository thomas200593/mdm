package com.thomas200593.mdm.app.main

import com.thomas200593.mdm.features.conf._common.Common
import com.thomas200593.mdm.features.conf._theme.Theme

sealed interface UiStateMain {
    data object Loading: UiStateMain
    data class Success(val data: Common): UiStateMain {
        override fun darkThemeEnabled(isSystemInDarkTheme: Boolean): Boolean =
            when (data.ui.theme) {
                Theme.SYSTEM -> isSystemInDarkTheme
                Theme.LIGHT -> false
                Theme.DARK -> true
            }
    }
    fun keepSplashScreenOn() = this is Loading
    fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
}