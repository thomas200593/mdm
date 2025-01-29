package com.thomas200593.mdm.app.main

sealed interface UiStateMain {
    data object Loading: UiStateMain
    data class Success(val data: Int): UiStateMain
    fun keepSplashScreenOn() = this is Loading
    fun darkThemeEnabled(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
}