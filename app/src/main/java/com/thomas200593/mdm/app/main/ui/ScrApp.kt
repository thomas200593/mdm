package com.thomas200593.mdm.app.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp

@Composable
fun ScrApp(stateApp: StateApp = LocalStateApp.current) {
    val isNetworkOffline by stateApp.isNetworkOffline.collectAsStateWithLifecycle()
}