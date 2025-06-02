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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.ImagesearchRoller
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.SessionHandler
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.common.AppIcons
import com.thomas200593.mdm.core.ui.common.anim.SlideUpFadeAnim
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.TxtMdLabel
import com.thomas200593.mdm.core.ui.component.TxtMdTitle
import com.thomas200593.mdm.core.ui.component.dialog.ErrorDialog
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.screen.InnerCircularProgressIndicator
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.core.ui.component.text_field.SearchToolBar
import com.thomas200593.mdm.features.auth.nav.navToAuth
import com.thomas200593.mdm.features.bootstrap.nav.navToBootstrap
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.ui.RoleInfoDialog
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.FormRoleSelectionState
import com.thomas200593.mdm.features.role_selection.ui.state.ResultSetUserRoleState
import com.thomas200593.mdm.features.role_selection.ui.state.ScreenDataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

@Composable fun ScrRoleSelection(
    scrGraph: ScrGraphs.RoleSelection, vm: VMRoleSelection = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val formRoleSelection = vm.formRoleSelection
    stateApp.SessionHandler(
        onLoading = { ev -> vm.onSessionEvent(event = Events.Session.Loading(ev = ev)) },
        onInvalid = { ev, err -> vm.onSessionEvent(event = Events.Session.Invalid(ev = ev, error = err)) },
        onNoCurrentRole = { ev, data -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data, currentRole = null)) },
        onValid = { ev, data, currentRole -> vm.onSessionEvent(event = Events.Session.Valid(ev = ev, data = data, currentRole = currentRole)) }
    )
    ScrRoleSelection(
        scrGraph = scrGraph,
        uiState = uiState,
        formRoleSelection = formRoleSelection,
        onTopBarEvent = { when (it) {
            is Events.TopBar.BtnSignOut.Clicked -> { vm.onTopBarEvent(it)
                .also { coroutineScope.launch { stateApp.navController.navToAuth() } } }
            else -> { vm.onTopBarEvent(it) }
        } },
        onFormEvent = { vm.onFormEvent(it) },
        onBottomBarEvent = { vm.onBottomBarEvent(it) },
        onUserRoleSetCallback = { vm.onUserRoleSetCallBackEvent(it).also { coroutineScope.launch { stateApp.navController.navToBootstrap() } } }
    )
}
@Composable private fun ScrRoleSelection(
    scrGraph : ScrGraphs.RoleSelection,
    uiState : VMRoleSelection.UiState,
    formRoleSelection : FormRoleSelectionState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit,
    onBottomBarEvent: (Events.BottomBar) -> Unit,
    onUserRoleSetCallback: (Events.Content.UserRoleSetCallback) -> Unit
) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> ScreenContent(
        scrGraph = scrGraph,
        screenData = uiState.screenData,
        dialog = uiState.dialog,
        resultSetRole = uiState.resultSetUserRole,
        formRoleSelection = formRoleSelection,
        onTopBarEvent = onTopBarEvent,
        onFormEvent = onFormEvent,
        onBottomBarEvent = onBottomBarEvent,
        onUserRoleSetCallback = onUserRoleSetCallback
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun HandleDialogs(
    scrGraph : ScrGraphs.RoleSelection,
    dialog : DialogState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onBottomBarEvent: (Events.BottomBar) -> Unit
) = when (dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        onDismissRequest = { onTopBarEvent(Events.TopBar.BtnScrDesc.Dismissed) },
        title = stringResource(scrGraph.title),
        description = stringResource(scrGraph.description)
    )
    is DialogState.SessionInvalid -> ErrorDialog(
        onDismissRequest = { onTopBarEvent(Events.TopBar.BtnSignOut.Clicked) },
        title = stringResource(R.string.str_session_invalid),
        message = dialog.error.message.toString(),
        error = dialog.error,
        btnConfirmText = stringResource(R.string.str_sign_in)
    )
    is DialogState.RoleInfo -> RoleInfoDialog(
        onDismissRequest = { onBottomBarEvent(Events.BottomBar.BtnRoleInfo.Dismissed) },
        role = dialog.role
    )
}
@Composable private fun ScreenContent(
    scrGraph: ScrGraphs.RoleSelection,
    screenData: ScreenDataState.Loaded,
    dialog: DialogState,
    resultSetRole: ResultSetUserRoleState,
    formRoleSelection: FormRoleSelectionState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit,
    onBottomBarEvent: (Events.BottomBar) -> Unit,
    onUserRoleSetCallback : (Events.Content.UserRoleSetCallback) -> Unit
) {
    LaunchedEffect(
        key1 = resultSetRole,
        block = { if (resultSetRole is ResultSetUserRoleState.Success) onUserRoleSetCallback(Events.Content.UserRoleSetCallback.Success) }
    )
    HandleDialogs(
        scrGraph = scrGraph, dialog = dialog,
        onTopBarEvent = onTopBarEvent, onBottomBarEvent = onBottomBarEvent
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(
            scrGraph = scrGraph,
            onTopBarEvent = onTopBarEvent
        ) },
        content = { SectionContent(
            paddingValues = it,
            screenData = screenData,
            formRoleSelection = formRoleSelection,
            onFormEvent = onFormEvent
        ) },
        bottomBar = { SlideUpFadeAnim(
            visible = (formRoleSelection.fldSelectedRole != null) && (formRoleSelection.btnProceedVisible),
            content = { SectionBottomBar(formRoleSelection = formRoleSelection, onBottomBarEvent = onBottomBarEvent) }
        ) }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection, onTopBarEvent: (Events.TopBar) -> Unit
) = TopAppBar(
    title = { Text(stringResource(scrGraph.title)) },
    actions = {
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnSignOut.Clicked) },
            content = { Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
        )
    }
)
@Composable private fun SectionContent (
    paddingValues : PaddingValues, screenData : ScreenDataState.Loaded, formRoleSelection : FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = Surface(
    modifier = Modifier.padding(paddingValues).fillMaxSize(),
    content = { Column (
        modifier = Modifier.fillMaxSize(),
        content = {
            val lazyPagingItems = screenData.roles.collectAsLazyPagingItems()
            when {
                screenData.userRolesCount <= 0 -> PartContentUserRoleEmpty()
                else -> {
                    PartContentUserRoleToolbar(
                        formRoleSelection = formRoleSelection,
                        onFormEvent = onFormEvent
                    )
                    when {
                        lazyPagingItems.itemCount <= 0 -> PartContentUserRoleSearchNoResult(formRoleSelection.fldSearchQuery)
                        else -> PartContentUserRoleResult(
                            formRoleSelection = formRoleSelection,
                            lazyPagingItems = lazyPagingItems,
                            onFormEvent = onFormEvent
                        )
                    }
                }
            }
        }
    ) }
)
@Composable private fun PartContentUserRoleEmpty() = Column (
    modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = { PanelCard(
        modifier = Modifier.padding(Constants.Dimens.dp16), title = {
            Icon(
                modifier = Modifier.fillMaxWidth(),
                imageVector = ImageVector.vectorResource(R.drawable.app_icon_sad_48px),
                contentDescription = null
            )
            HorizontalDivider()
        },
        content = { TxtMdBody(stringResource(R.string.str_user_have_no_roles_assoc)) }
    ) }
)
@Composable private fun PartContentUserRoleSearchNoResult(query: String) = Column (
    modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = { PanelCard(
        modifier = Modifier.padding(Constants.Dimens.dp16), title = {
            Icon(modifier = Modifier.fillMaxWidth(),
                imageVector = Icons.Default.ImagesearchRoller, contentDescription = null)
            HorizontalDivider()
        },
        content = { TxtMdBody("No roles found for keyword '$query'.") }
    ) }
)
@Composable private fun PartContentUserRoleToolbar(
    formRoleSelection: FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = Row (
    modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp4),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    content = {
        SearchToolBar(
            query = formRoleSelection.fldSearchQuery,
            onQueryChanged = { onFormEvent(Events.Content.Form.SearchBar.QueryChanged(it)) },
            onSearchTriggered = { onFormEvent(Events.Content.Form.SearchBar.QueryChanged(it)) },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { onFormEvent( when (formRoleSelection.fldLayoutMode) {
                FormRoleSelectionState.Companion.LayoutMode.List -> Events.Content.Form.LayoutType.Grid
                FormRoleSelectionState.Companion.LayoutMode.Grid -> Events.Content.Form.LayoutType.List
            } ) },
            content = { Icon(imageVector = when (formRoleSelection.fldLayoutMode) {
                FormRoleSelectionState.Companion.LayoutMode.List -> Icons.AutoMirrored.Filled.List
                FormRoleSelectionState.Companion.LayoutMode.Grid -> Icons.Default.GridOn },
                contentDescription = null) },
            modifier = Modifier
        )
        IconButton(
            onClick = { onFormEvent(Events.Content.Form.ModalBottomSheet.Clicked) /*TODO*/ },
            content = { Icon(imageVector = Icons.Default.FilterList, contentDescription = null) },
            modifier = Modifier
        )
    }
)
@Composable private fun PartContentUserRoleResult(
    formRoleSelection: FormRoleSelectionState, lazyPagingItems: LazyPagingItems<RoleEntity>,
    onFormEvent: (Events.Content.Form) -> Unit
) {
    when(formRoleSelection.fldLayoutMode) {
        FormRoleSelectionState.Companion.LayoutMode.List -> PartContentUserRoleList(
            lazyPagingItems = lazyPagingItems,
            formRoleSelection = formRoleSelection,
            onFormEvent = onFormEvent
        )
        FormRoleSelectionState.Companion.LayoutMode.Grid -> PartContentUserRoleGrid(
            lazyPagingItems = lazyPagingItems,
            formRoleSelection = formRoleSelection,
            onFormEvent = onFormEvent
        )
    }
}
@Composable private fun PartContentUserRoleList(
    lazyPagingItems: LazyPagingItems<RoleEntity>, formRoleSelection: FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = LazyColumn (
    modifier = Modifier.fillMaxSize(),
    content = { items(lazyPagingItems.itemCount) { index -> lazyPagingItems[index]
        ?.let { role -> ItemListRole(
            role = role,
            formRoleSelection = formRoleSelection,
            onFormEvent = onFormEvent
        ) } }
    }
)
@Composable private fun PartContentUserRoleGrid(
    lazyPagingItems: LazyPagingItems<RoleEntity>, formRoleSelection: FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = LazyVerticalGrid (
    modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(3),
    content = { items(lazyPagingItems.itemCount) { index -> lazyPagingItems[index]
        ?.let { role -> ItemGridRole(
            role = role,
            formRoleSelection = formRoleSelection,
            onFormEvent = onFormEvent
        ) } }
    }
)
@Composable private fun ItemListRole(
    role: RoleEntity, formRoleSelection: FormRoleSelectionState,
    onFormEvent : (Events.Content.Form) -> Unit
) = Card (
    modifier = Modifier.padding(Constants.Dimens.dp4).clickable(
        onClick = { onFormEvent(Events.Content.Form.SelectedRole(role)) }
    ),
    border = BorderStroke(
        width = Constants.Dimens.dp1,
        color =
            if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline
    ),
    colors = CardDefaults.cardColors().copy(
        containerColor =
            if(formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.tertiaryContainer,
        contentColor =
            if(formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.onSecondaryContainer
            else MaterialTheme.colorScheme.onTertiaryContainer
    ),
    content = { Row (
        modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8),
        horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            val imgModel = File(role.image)
                .takeIf { role.image.isNotEmpty() && role.image != Constants.STR_SYSTEM && it.exists() }
                ?: AppIcons.App.icon
            Surface(
                shape = RoundedCornerShape(Constants.Dimens.dp8),
                border = BorderStroke(
                    width = Constants.Dimens.dp1,
                    color =
                        if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                ),
                tonalElevation = Constants.Dimens.dp2,
                modifier = Modifier.size(Constants.Dimens.dp48),
                content = { SubcomposeAsyncImage(
                    model = imgModel, contentDescription = null, loading = { InnerCircularProgressIndicator() },
                    contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                ) }
            )
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp4),
                content = {
                    TxtMdTitle(
                        text = role.label, modifier = Modifier.fillMaxWidth(),
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Card(
                        modifier = Modifier, border = BorderStroke(
                            width = Constants.Dimens.dp1,
                            color =
                                if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline
                        ),
                        shape = MaterialTheme.shapes.extraSmall,
                        content = { TxtMdLabel(
                            text = role.roleType.toString(), maxLines = 1,
                            modifier = Modifier.padding(Constants.Dimens.dp4), overflow = TextOverflow.Ellipsis
                        ) }
                    )
                }
            )
        }
    ) }
)
@Composable private fun ItemGridRole(
    role : RoleEntity, formRoleSelection : FormRoleSelectionState,
    onFormEvent : (Events.Content.Form) -> Unit
) = Card(
    modifier = Modifier.padding(Constants.Dimens.dp8).clickable(
        onClick = { onFormEvent(Events.Content.Form.SelectedRole(role)) }
    ),
    shape = MaterialTheme.shapes.extraSmall,
    border = BorderStroke(
        width = Constants.Dimens.dp1,
        color =
            if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline
    ),
    colors = CardDefaults.cardColors().copy(
        containerColor =
            if(formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.tertiaryContainer,
        contentColor =
            if(formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.onSecondaryContainer
            else MaterialTheme.colorScheme.onTertiaryContainer
    ),
    content = { Column (
        modifier = Modifier.padding(Constants.Dimens.dp8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8, Alignment.CenterVertically),
        content = {
            val imgModel = File(role.image)
                .takeIf { role.image.isNotEmpty() && role.image != Constants.STR_SYSTEM && it.exists() }
                ?: AppIcons.App.icon
            Surface(
                shape = RoundedCornerShape(Constants.Dimens.dp8),
                border = BorderStroke(
                    width = Constants.Dimens.dp1,
                    color =
                        if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                ),
                tonalElevation = Constants.Dimens.dp2,
                modifier = Modifier.size(Constants.Dimens.dp100),
                content = { SubcomposeAsyncImage(
                    model = imgModel, contentDescription = null,
                    loading = { InnerCircularProgressIndicator() },
                    contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                ) }
            )
            TxtMdTitle(
                text = role.label, modifier = Modifier.fillMaxWidth(), maxLines = 1,
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis
            )
        }
    ) }
)
@Composable private fun SectionBottomBar(
    formRoleSelection: FormRoleSelectionState, onBottomBarEvent : (Events.BottomBar) -> Unit
) = BottomAppBar(
    modifier = Modifier.fillMaxWidth(),
    content = { Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        content = {
            IconButton(
                onClick = { formRoleSelection.fldSelectedRole
                    ?.let { onBottomBarEvent(Events.BottomBar.BtnRoleInfo.Clicked(it)) } },
                content = { Icon(imageVector = Icons.Default.Assistant, contentDescription = null) }
            )
            Button(
                onClick = { formRoleSelection.fldSelectedRole
                    ?.let { onBottomBarEvent(Events.BottomBar.BtnConfirmRole.Clicked(it)) } },
                content = { Text(stringResource(R.string.str_select)) },
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.weight(1f)
            )
        }
    ) }
)