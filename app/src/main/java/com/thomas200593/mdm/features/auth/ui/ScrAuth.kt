package com.thomas200593.mdm.features.auth.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrAuth() {
    ScreenContent()
}

@Composable
private fun ScreenContent() = Scaffold(
    topBar = {},
    content = {
        Surface(modifier = Modifier.padding(it)) {  }
    }
)