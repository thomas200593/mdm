package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.TxtMdLabel
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.screen.InnerCircularProgressIndicator
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.LayoutMode
import com.thomas200593.mdm.features.role_selection.ui.state.ScreenDataState
import kotlinx.coroutines.CoroutineScope
import java.io.File

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
        scrGraph = scrGraph, dialog = dialog
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(scrGraph = scrGraph, screenData = screenData) },
        content = {
            SectionContent(
                paddingValues = it,
                screenData = screenData,
                onSelectedRole = onSelectedRole
            )
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection,
    screenData: ScreenDataState.Loaded
) = TopAppBar(
    title = { Text(stringResource(scrGraph.title)) },
    actions = {
        IconButton(
            onClick = { /*TODO*/ },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        IconButton(
            onClick = { /*TODO*/ },
            content = { Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
        )
    }
)
@Composable private fun SectionContent (
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
    content = {
        PanelCard(
            modifier = Modifier.padding(Constants.Dimens.dp16),
            title = {
                Icon(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = ImageVector.vectorResource(R.drawable.app_icon_sad_48px),
                    contentDescription = null
                )
                HorizontalDivider()
            },
            content = {
                TxtMdBody("This user has no role associate with, please contact the System Administrator!.")
            }
        )
    }
)
@Composable private fun PartContentUserRoleList(
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = LazyColumn (
    modifier = Modifier.fillMaxSize(),
    content = { items(lazyPagingItems.itemCount) { index ->
        lazyPagingItems[index]?.let { role ->
            ItemListRole(
                role = role,
                screenData = screenData,
                onSelectedRole = onSelectedRole
            )
        }
    } }
)
@Composable private fun PartContentUserRoleGrid(
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    screenData: ScreenDataState.Loaded,
    onSelectedRole: (RoleEntity) -> Unit
) = LazyVerticalGrid (
    modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(2),
    content = { items(lazyPagingItems.itemCount) { index ->
        lazyPagingItems[index]?.let { role ->
            ItemGridRole(
                role = role,
                screenData = screenData,
                onSelectedRole = onSelectedRole
            )
        }
    } }
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
    modifier = Modifier.padding(Constants.Dimens.dp4).clickable(onClick = { onSelectedRole(role) }),
    border = BorderStroke(
        width = Constants.Dimens.dp1,
        color = if (screenData.selectedRole == role) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline
    ),
    content = {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            content = {
                val imgModel = when {
                    role.image.isEmpty() || role.image == Constants.STR_SYSTEM -> R.drawable.app_icon_48x48px
                    else -> File(role.image)
                }
                Surface(
                    shape = RoundedCornerShape(Constants.Dimens.dp8),
                    border = BorderStroke(
                        width = Constants.Dimens.dp1,
                        color = if (screenData.selectedRole == role) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    ),
                    tonalElevation = Constants.Dimens.dp2,
                    modifier = Modifier.size(Constants.Dimens.dp64)
                ) {
                    SubcomposeAsyncImage(
                        model = imgModel,
                        contentDescription = null,
                        loading = { InnerCircularProgressIndicator() },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8, Alignment.CenterVertically),
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TxtMdBody(
                                text = role.label,
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            TxtMdLabel(
                                text = role.roleType.toString()
                            )
                        }
                        TxtMdLabel(
                            text = role.description,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                )
            }
        )
    },
    colors = CardDefaults.cardColors().copy(
        containerColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = if(screenData.selectedRole == role) MaterialTheme.colorScheme.onSecondaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer
    )
)