package com.thomas200593.mdm.core.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

object Theme {
    private val lightContrastDefault = Color.Light.ContrastDefault.colorScheme
    private val lightContrastMedium = Color.Light.ContrastMedium.colorScheme
    private val lightContrastHigh = Color.Light.ContrastHigh.colorScheme
    private val darkContrastDefault = Color.Dark.ContrastDefault.colorScheme
    private val darkContrastMedium = Color.Dark.ContrastMedium.colorScheme
    private val darkContrastHigh = Color.Dark.ContrastHigh.colorScheme

    @Composable
    fun AppTheme(
        content: @Composable () -> Unit
    ) {
        val colorScheme = when {
            else -> 1 /*TODO*/
        }
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme,
            typography = MaterialTheme.typography,
            content = content
        )
    }
}