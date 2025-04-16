package com.thomas200593.mdm.core.ui.component

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.util.Consumer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
fun ComponentActivity.isSystemInDarkTheme() = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)
    val listener = Consumer<Configuration> { channel.trySend(it.isSystemInDarkTheme) }
    addOnConfigurationChangedListener(listener)
    awaitClose { removeOnConfigurationChangedListener(listener) }
}.distinctUntilChanged().conflate()
fun setupSplashScreen(splashScreen: SplashScreen) {
    splashScreen.setOnExitAnimationListener { obj ->
        val slideBack = ObjectAnimator.ofFloat(obj.view, View.TRANSLATION_X, 0f, -obj.view.width.toFloat())
            .apply { interpolator = DecelerateInterpolator(); duration = 400L; doOnEnd { obj.remove() } }
        slideBack.start()
    }
}