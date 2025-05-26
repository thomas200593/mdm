package com.thomas200593.mdm.features.user_management.user_role.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ViewModule
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity
import com.thomas200593.mdm.features.user_management.user_role.ui.events.Events
import com.thomas200593.mdm.features.user_management.user_role.ui.state.DialogState
import com.thomas200593.mdm.features.user_management.user_role.ui.state.LayoutMode
import com.thomas200593.mdm.features.user_management.user_role.ui.state.ScreenDataState
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    stateApp.SessionHandler(
        onLoading = { ev -> vm.onSessionEvent(event = Events.Session.Loading(ev = ev)) },
        onInvalid = { ev, err -> vm.onSessionEvent(event = Events.Session.Invalid(ev = ev, error = err)) },
        onNoCurrentRole = { ev, data -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data, currentRole = null)) },
        onValid = { ev, data, currentRole -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data, currentRole = currentRole)) }
    )
    ScrRoleSelection(
        scrGraph = scrGraph,
        uiState = uiState,
        onSelectedRole = { vm.onSelectedRole(it) },
    )
}
@Composable private fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection,
    uiState: VMRoleSelection.UiState,
    onSelectedRole: (RoleEntity) -> Unit,
) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> ScreenContent(
        scrGraph = scrGraph,
        screenData = uiState.screenData,
        dialog = uiState.dialog,
        onSelectedRole = onSelectedRole
    )
}
@Composable private fun HandleDialogs(
    scrGraph: ScrGraphs.RoleSelection,
    dialog: DialogState
) = when (dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        onDismissRequest = { /*TODO*/ },
        title = stringResource(scrGraph.title),
        description = stringResource(scrGraph.description)
    )
    is DialogState.SessionInvalid -> Unit
}
@Composable private fun ScreenContent(
    scrGraph: ScrGraphs.RoleSelection,
    screenData: ScreenDataState.Loaded,
    dialog: DialogState,
    onSelectedRole: (RoleEntity) -> Unit
) {
    HandleDialogs(
        scrGraph = scrGraph,
        dialog = dialog
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(screenData = screenData) },
        content = {
            SectionContent(
                paddingValues = it,
                screenData = screenData,
                onSelectedRole = onSelectedRole
            )
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(screenData: ScreenDataState.Loaded) = TopAppBar(
    title = {},
    actions = {
        IconButton(
            onClick = {/*TODO*/},
            content = {
                Icon(
                    imageVector = if (screenData.layoutMode == LayoutMode.List)
                        Icons.Default.ViewModule else Icons.AutoMirrored.Filled.ViewList,
                    contentDescription = "Toggle Layout"
                )
            }
        )
        IconButton(
            onClick = { /*TODO*/ },
            content = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            }
        )
    }
)
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionContent (
    paddingValues: PaddingValues,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = Surface(
    modifier = Modifier.padding(paddingValues).fillMaxSize(),
    content = {
        val lazyPagingItems = screenData.roles.collectAsLazyPagingItems()
        lazyPagingItems.itemCount.takeIf { it <= 0 }
            ?. let { PartContentUserRoleEmpty() }
            ?: when(screenData.layoutMode) {
                LayoutMode.List -> PartContentUserRoleList(
                    lazyPagingItems = lazyPagingItems,
                    screenData = screenData,
                    onSelectedRole = onSelectedRole
                )
                LayoutMode.Grid -> PartContentUserRoleGrid(
                    lazyPagingItems = lazyPagingItems,
                    screenData = screenData,
                    onSelectedRole = onSelectedRole
                )
            }
    }
)
@Composable private fun PartContentUserRoleEmpty() = Column (
    modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = { PanelCard(
        modifier = Modifier.padding(Constants.Dimens.dp16),
        content = { TxtMdBody("This user has no role associate with, please contact the System Administrator!.") }
    ) }
)
@Composable private fun PartContentUserRoleList(
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = LazyColumn (
    modifier = Modifier.fillMaxSize(),
    content = {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { role ->
                ItemListRole(
                    role = role,
                    screenData = screenData,
                    onSelectedRole = onSelectedRole
                )
            }
        }
    }
)
@Composable private fun PartContentUserRoleGrid(
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = LazyVerticalGrid (
    modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(2),
    content = {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { role ->
                ItemGridRole(
                    role = role,
                    screenData = screenData,
                    onSelectedRole = onSelectedRole
                )
            }
        }
    }
)
@Composable private fun ItemGridRole(
    role: RoleEntity,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = PanelCard(
    modifier = Modifier.padding(Constants.Dimens.dp8).clickable(onClick = { onSelectedRole(role) }),
    title = { Text(role.roleCode) },
    content = { Text(role.label) },
    footer = { Text(role.roleType.toString()) },
    colors = CardDefaults.cardColors().copy(
        containerColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.onSecondaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer
    )
)
@Composable private fun ItemListRole(
    role: RoleEntity,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = PanelCard(
    modifier = Modifier.clickable(onClick = { onSelectedRole(role) }),
    content = { Text(role.label) },
    colors = CardDefaults.cardColors().copy(
        containerColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.secondaryContainer
                         else MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.onSecondaryContainer
                       else MaterialTheme.colorScheme.onTertiaryContainer
    )
)