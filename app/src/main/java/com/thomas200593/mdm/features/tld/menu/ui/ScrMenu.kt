package com.thomas200593.mdm.features.tld.menu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.features.auth.nav.navToAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrMenu(
    scrGraph: ScrGraphs.DestTopLevel.Menu,
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    Column {
        Text("Screen : ${stringResource(scrGraph.title)}")
        Button(
            onClick = { coroutineScope.launch { stateApp.navController.navToAuth() } },
            content = {
                Text("Logout")
            }
        )
    }
}