package com.thomas200593.mdm.features.onboarding.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrOnboarding() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = { Text("Actions") }
            )
        },
        content = {
            Surface(modifier = Modifier.padding(it)) {
                Text("Onboarding Body")
            }
        }
    )
}

@Composable
@Preview
private fun Test() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = true,
    contrastAccent = ContrastAccent.DEFAULT,
    fontSize = FontSize.MEDIUM
) { ScrOnboarding() }