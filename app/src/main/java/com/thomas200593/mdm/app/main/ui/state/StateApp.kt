package com.thomas200593.mdm.app.main.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberStateApp() : StateApp =
    remember {
        StateApp()
    }

@Stable
class StateApp