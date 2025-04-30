package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.dialog.ErrorDialog
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.ComponentsState
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrRoleSelection(
        scrGraph = scrGraph,
        components = uiState.componentsState,
        testDeleteSession = { vm.testDeleteSession() },
        testDeleteUser = { vm.testDeleteUser() }
    )
    stateApp.SessionHandler()
}
@Composable private fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, components: ComponentsState, testDeleteSession: () -> Unit, testDeleteUser : () -> Unit
) = when (components) {
    is ComponentsState.Loading -> ScrLoading()
    is ComponentsState.Loaded -> ScreenContent(scrGraph = scrGraph, components = components, testDeleteSession = testDeleteSession, testDeleteUser = testDeleteUser)
}
@Composable private fun ScreenContent(scrGraph: ScrGraphs.RoleSelection, components: ComponentsState.Loaded, testDeleteSession: () -> Unit, testDeleteUser : () -> Unit) {
    HandleDialogs(dialog = components.dialogState, scrGraph = scrGraph)
    Scaffold(
        modifier = Modifier,
        topBar = { SectionTopBar(scrGraph = scrGraph, testDeleteSession = testDeleteSession, testDeleteUser = testDeleteUser) },
        content = { SectionContent(paddingValues = it, components) }
    )
}
@Composable private fun HandleDialogs(dialog: DialogState, scrGraph: ScrGraphs.RoleSelection) = when (dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        onDismissRequest = {},
        title = stringResource(scrGraph.title), description = stringResource(scrGraph.description)
    )
    is DialogState.SessionInvalidDialog -> ErrorDialog(
        onDismissRequest = {},
        title = "Something was wrong",
        message = "Error : ${dialog.t?.message.orEmpty()}; Stacktrace : ${dialog.t?.cause.toString()}"
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection,
    testDeleteSession : () -> Unit,
    testDeleteUser : () -> Unit
) = TopAppBar(
    title = { Text(stringResource(scrGraph.title)) }, actions = {
        IconButton(
            onClick = { testDeleteUser() },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        IconButton(
            onClick = { testDeleteSession() },
            content = { Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
        )
    }
)
@Composable private fun SectionContent(
    paddingValues: PaddingValues,
    components: ComponentsState.Loaded
) = Surface (
    modifier = Modifier.padding(paddingValues),
    content = { Text(components.toString()) }
)