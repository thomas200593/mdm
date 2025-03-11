package com.thomas200593.mdm.features.initialization.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrInitialization() {
    ScreenContent()
}

@Composable
private fun ScreenContent() = Scaffold(
    topBar = {},
    content = {
        Surface(
            modifier = Modifier.padding(it),
            content = {
                Text("Initialization")
            }
        )
    }
)