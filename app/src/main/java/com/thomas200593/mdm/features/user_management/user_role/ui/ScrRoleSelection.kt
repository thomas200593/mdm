package com.thomas200593.mdm.features.user_management.user_role.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.user_management.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ScreenDataState
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    stateApp.SessionHandler(
        onLoading = { ev -> },
        onInvalid = { ev, t -> },
        onNoCurrentRole = { ev, data -> },
        onValid = { ev, data, currentRole -> }
    )
    ScrRoleSelection(uiState = uiState)
}
@Composable private fun ScrRoleSelection(uiState: VMRoleSelection.UiState) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> {
        val roles = uiState.screenData.roles.collectAsLazyPagingItems()
        when (roles.loadState.refresh) {
            is LoadState.Error -> TODO()
            is LoadState.Loading -> TODO()
            is LoadState.NotLoading -> TODO()
        }
    }
}
@Composable private fun ScreenContent(
    scrGraph: ScrGraphs.RoleSelection,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) {
    HandleDialogs()
    Scaffold(
        modifier = Modifier,
        topBar = {
            SectionTopBar(
                scrGraph = scrGraph,
                onTopBarEvent = onTopBarEvent,
                onSignOutEvent = onSignOutEvent
            )
        },
        content = { SectionContent(paddingValues = it) },
        bottomBar = { SectionBottomBar() }
    )
}
@Composable private fun HandleDialogs() {}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection,
    onTopBarEvent : (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) = TopAppBar(
    title = { Text(stringResource(scrGraph.title)) },
    actions = {
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        IconButton(
            onClick = { onSignOutEvent(Events.Dialog.ErrorDismissed) },
            content = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
)
@Composable private fun SectionContent(
    paddingValues: PaddingValues
) = Surface (
    modifier = Modifier.padding(paddingValues).fillMaxSize(),
    content = {
        Column (
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = {

            }
        )
    }
)
@Composable private fun SectionBottomBar() {/*TODO*/}