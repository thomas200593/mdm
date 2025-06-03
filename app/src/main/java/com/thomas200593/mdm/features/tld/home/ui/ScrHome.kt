package com.thomas200593.mdm.features.tld.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrHome(
    scrGraph: ScrGraphs.DestTopLevel.Home,
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    ScrHome()
}
@Composable private fun ScrHome() {
    ScreenContent()
}
@Composable private fun ScreenContent() {
    SectionContent()
}
@Composable private fun SectionContent() = Surface(
    modifier = Modifier.fillMaxSize(),
    content = {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            content = {
                item { Text("Home page") }
            }
        )
    }
)