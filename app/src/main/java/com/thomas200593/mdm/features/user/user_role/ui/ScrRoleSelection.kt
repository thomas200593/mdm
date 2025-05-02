package com.thomas200593.mdm.features.user.user_role.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.TxtMdTitle
import com.thomas200593.mdm.core.ui.component.dialog.ErrorDialog
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.screen.InnerCircularProgressIndicator
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.user.user_role.ui.events.Events
import com.thomas200593.mdm.features.user.user_role.ui.state.ComponentsState
import com.thomas200593.mdm.features.user.user_role.ui.state.DialogState
import com.thomas200593.mdm.features.user.user_role.ui.state.ResultGetUserRole
import com.thomas200593.mdm.features.security.auth.nav.navToAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrRoleSelection(
        scrGraph = scrGraph,
        components = uiState.componentsState,
        onTopBarEvent = { vm.onTopBarEvent(it) },
        onSignOutEvent = { vm.onDialogEvent(it)
            .also { coroutineScope.launch { stateApp.navController.navToAuth() } } }
    )
    stateApp.SessionHandler(
        onLoading = { ev -> vm.onSessionEvent(event = Events.Session.Loading(ev = ev)) },
        onInvalid = { ev, t -> vm.onSessionEvent(event = Events.Session.Invalid(ev = ev, t = t)) },
        onNoCurrentRole = { ev, data -> vm.onSessionEvent(event = Events.Session.NoCurrentRole(ev = ev, data = data)) },
        onValid = { ev, data -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data)) }
    )
    vm.testDeleteUserRole()
}
@Composable private fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, components: ComponentsState, onTopBarEvent: (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) = when (components) {
    is ComponentsState.Loading -> ScrLoading()
    is ComponentsState.Loaded -> ScreenContent(
        scrGraph = scrGraph,
        components = components,
        onTopBarEvent = onTopBarEvent,
        onSignOutEvent = onSignOutEvent
    )
}
@Composable private fun ScreenContent(
    scrGraph: ScrGraphs.RoleSelection, components: ComponentsState.Loaded, onTopBarEvent: (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) {
    HandleDialogs(
        dialog = components.dialogState, scrGraph = scrGraph, onTopBarEvent = onTopBarEvent,
        onSignOutEvent = onSignOutEvent
    )
    Scaffold(
        modifier = Modifier,
        topBar = { SectionTopBar(scrGraph = scrGraph, onTopBarEvent = onTopBarEvent, onSignOutEvent = onSignOutEvent) },
        content = { SectionContent(paddingValues = it, components = components) },
        bottomBar = { SectionBottomBar() }
    )
}
@Composable private fun HandleDialogs(
    dialog: DialogState, scrGraph: ScrGraphs.RoleSelection, onTopBarEvent: (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) = when (dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        onDismissRequest = { onTopBarEvent(Events.TopBar.BtnScrDesc.Dismissed) },
        title = stringResource(scrGraph.title), description = stringResource(scrGraph.description)
    )
    is DialogState.SessionInvalidDialog -> ErrorDialog(
        onDismissRequest = { onSignOutEvent(Events.Dialog.ErrorDismissed) },
        title = "Something was wrong",
        message = "Error : ${dialog.t?.message.orEmpty()}; Stacktrace : ${dialog.t?.cause.toString()}"
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection, onTopBarEvent : (Events.TopBar) -> Unit,
    onSignOutEvent: (Events.Dialog.ErrorDismissed) -> Unit
) = TopAppBar(
    title = { Text(stringResource(scrGraph.title)) }, actions = {
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        IconButton(
            onClick = { onSignOutEvent(Events.Dialog.ErrorDismissed) },
            content = { Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null,
                tint = MaterialTheme.colorScheme.error) }
        )
    }
)
@Composable private fun SectionContent(paddingValues: PaddingValues, components: ComponentsState.Loaded) = Surface (
    modifier = Modifier.padding(paddingValues).fillMaxSize(),
    content = { Column (
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            when(components.resultGetUserRole) {
                is ResultGetUserRole.Loading -> InnerCircularProgressIndicator()
                is ResultGetUserRole.Error -> when (components.resultGetUserRole.t) {
                    is Error.Database.DaoQueryNoDataError -> SectionRoleSelectionNoData(components.resultGetUserRole.t)
                    is Error.Database.DaoQueryError -> SectionRoleSelectionError(components.resultGetUserRole.t)
                    else -> SectionRoleSelectionError(components.resultGetUserRole.t)
                }
                is ResultGetUserRole.Success -> SectionRoleSelection(components)
            }
        }
    ) }
)
@Composable private fun SectionRoleSelectionError(throwable: Throwable) {/*TODO*/}
@Composable private fun SectionRoleSelectionNoData(throwable: Error.Database.DaoQueryNoDataError) = Card(
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    content = { Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        content = {
            TxtMdTitle("Oops! You don’t have any roles assigned yet, please contact your System Administrator.")
        }
    ) }
)
@Composable private fun SectionRoleSelection(components: ComponentsState.Loaded) = Card (
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    content = { Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        content = {
            TxtMdTitle("To continue, please select your role")
            TxtMdBody("Role list")
            /*TODO*/
            /*RoleDropdown(
                roles = components.roles,
                selectedRole = components.selectedRole,
                onRoleSelected = components.onRoleSelected
            )*/
        }
    ) }
)
@Composable private fun SectionBottomBar() {/*TODO*/}