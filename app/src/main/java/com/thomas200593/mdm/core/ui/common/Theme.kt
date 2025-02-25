package com.thomas200593.mdm.core.ui.common

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

object Theme {
    private val lightContrastDefault = Color.Light.ContrastDefault.colorScheme
    private val lightContrastMedium = Color.Light.ContrastMedium.colorScheme
    private val lightContrastHigh = Color.Light.ContrastHigh.colorScheme
    private val darkContrastDefault = Color.Dark.ContrastDefault.colorScheme
    private val darkContrastMedium = Color.Dark.ContrastMedium.colorScheme
    private val darkContrastHigh = Color.Dark.ContrastHigh.colorScheme
    private val shapes = Shapes()
    @Composable
    fun AppTheme(
        darkThemeEnabled: Boolean = isSystemInDarkTheme(),
        dynamicColorEnabled: Boolean, // Dynamic color is available on Android 12+
        contrastAccent: ContrastAccent,
        fontSize: FontSize,
        content: @Composable () -> Unit
    ) {
        val context = LocalContext.current
        val colorScheme = when {
            // Case 1: Dynamic color is supported and enabled
            supportDynamicColor() && dynamicColorEnabled ->
                if (darkThemeEnabled) dynamicDarkColorScheme(context)
                else dynamicLightColorScheme(context)
            // Case 2: Not using dynamic color, dark mode is enabled
            darkThemeEnabled -> when (contrastAccent) {
                ContrastAccent.DEFAULT -> darkContrastDefault
                ContrastAccent.MEDIUM -> darkContrastMedium
                ContrastAccent.HIGH -> darkContrastHigh
            }
            // Case 3: Not using dynamic color, light mode
            else -> when (contrastAccent) {
                ContrastAccent.DEFAULT -> lightContrastDefault
                ContrastAccent.MEDIUM -> lightContrastMedium
                ContrastAccent.HIGH -> lightContrastHigh
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = Typography.custom(fontSize = fontSize),
            content = content
        )
    }
    private fun supportDynamicColor() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}