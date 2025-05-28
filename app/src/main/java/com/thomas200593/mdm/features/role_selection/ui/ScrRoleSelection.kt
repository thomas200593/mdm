package com.thomas200593.mdm.features.role_selection.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType
import com.thomas200593.mdm.features.role_selection.ui.events.Events
import com.thomas200593.mdm.features.role_selection.ui.state.DialogState
import com.thomas200593.mdm.features.role_selection.ui.state.FormRoleSelectionState
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
        onBottomBarEvent = { vm.onBottomBarEvent(it) }
    )
}
@Composable private fun ScrRoleSelection(
    scrGraph : ScrGraphs.RoleSelection,
    uiState : VMRoleSelection.UiState,
    formRoleSelection : FormRoleSelectionState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit,
    onBottomBarEvent: (Events.BottomBar) -> Unit
) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> ScreenContent(
        scrGraph = scrGraph,
        screenData = uiState.screenData,
        dialog = uiState.dialog,
        formRoleSelection = formRoleSelection,
        onTopBarEvent = onTopBarEvent,
        onFormEvent = onFormEvent,
        onBottomBarEvent = onBottomBarEvent
    )
}
@Composable private fun HandleDialogs(
    scrGraph : ScrGraphs.RoleSelection,
    dialog : DialogState,
    onTopBarEvent: (Events.TopBar) -> Unit
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
}
@Composable private fun ScreenContent(
    scrGraph : ScrGraphs.RoleSelection,
    screenData : ScreenDataState.Loaded,
    dialog : DialogState,
    formRoleSelection : FormRoleSelectionState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit,
    onBottomBarEvent: (Events.BottomBar) -> Unit
) {
    HandleDialogs(
        scrGraph = scrGraph, dialog = dialog, onTopBarEvent = onTopBarEvent
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
        bottomBar = { AnimatedVisibility(
            visible = (formRoleSelection.fldSelectedRole != null) && (formRoleSelection.btnProceedVisible),
            content = { SectionBottomBar(formRoleSelection = formRoleSelection, onBottomBarEvent = onBottomBarEvent) }
        ) }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    scrGraph: ScrGraphs.RoleSelection,
    onTopBarEvent : (Events.TopBar) -> Unit
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
    paddingValues : PaddingValues,
    screenData : ScreenDataState.Loaded,
    formRoleSelection : FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = Surface(
    modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
    content = { Column (
        modifier = Modifier.fillMaxSize(),
        content = {
            PartContentUserRoleToolbar(
                formRoleSelection = formRoleSelection,
                onFormEvent = onFormEvent
            )
            val lazyPagingItems = screenData.roles.collectAsLazyPagingItems()
            lazyPagingItems.itemCount.takeIf { it <= 0 }
                ?. let { PartContentUserRoleEmpty() }
                ?: when(formRoleSelection.fldLayoutMode) {
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
    ) }
)
@Composable private fun PartContentUserRoleEmpty() = Column (
    modifier = Modifier
        .fillMaxSize()
        .padding(Constants.Dimens.dp16),
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
@Composable private fun PartContentUserRoleToolbar(
    formRoleSelection: FormRoleSelectionState,
    onFormEvent: (Events.Content.Form) -> Unit
) = Row (
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    content = {
        SearchToolBar(
            query = formRoleSelection.fldSearchQuery,
            onQueryChanged = { onFormEvent(Events.Content.Form.SearchBar.QueryChanged(it)) },
            onSearchTriggered = { onFormEvent(Events.Content.Form.SearchBar.QueryChanged(it)) },
            modifier = Modifier.weight(.9f)
        )
        IconButton(
            onClick = { onFormEvent(Events.Content.Form.ModalBottomSheetSortFilter.Clicked) },
            content = { Icon(imageVector = Icons.Default.FilterList, contentDescription = null) },
            modifier = Modifier.weight(.1f)
        )
    }
)
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun PartContentModalBottomSheet(
    sheetState : SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    currentFilter : RoleType?,
    currentSort : FormRoleSelectionState.Companion.SortOption,
    onFilterSelected : (RoleType?) -> Unit,
    onSortSelected : (FormRoleSelectionState.Companion.SortOption) -> Unit,
    onApply : () -> Unit,
    onDismiss : () -> Unit
) {
    val roleTypes = setOf<RoleType>(RoleType.BuiltIn, RoleType.UserDefined)
    ModalBottomSheet(
        onDismissRequest = { /*TODO*/ },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        content = {
            /** TODO
             * Filterable:
             * 1. Sort by:
             *    - Role Type (Ascending)
             *    - Role Type (Descending)
             *    - Label (A–Z)
             *    - Label (Z–A)
             *    - Role Code (Ascending)
             *    - Role Code (Descending)
             *    - Created At (Oldest First)
             *    - Created At (Newest First)
             * 2. Filter by:
             *    - Role Type
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // FILTER SECTION
                Text("Filter by Role Type", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                FlowRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterChip(
                        selected = currentFilter == null,
                        onClick = { onFilterSelected(null) },
                        label = { Text("All") }
                    )
                    roleTypes.forEach { type ->
                        FilterChip(
                            selected = currentFilter == type,
                            onClick = { onFilterSelected(type) },
                            label = { Text(type::class.simpleName ?: "Unknown") }
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                // SORT SECTION
                Text("Sort By", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    FormRoleSelectionState.Companion.SortOption.entries.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onSortSelected(option) },
                            content = {
                                RadioButton(selected = currentSort == option, onClick = { onSortSelected(option) })
                                Spacer(Modifier.width(8.dp))
                                Text(stringResource(option.label))
                            }
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                // APPLY BUTTON
                Button(
                    onClick = onApply,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Apply") }
            }
        }
    )
}
@Composable private fun PartContentUserRoleList(
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    formRoleSelection: FormRoleSelectionState,
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
    lazyPagingItems: LazyPagingItems<RoleEntity>,
    formRoleSelection: FormRoleSelectionState,
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
    role: RoleEntity,
    formRoleSelection: FormRoleSelectionState,
    onFormEvent : (Events.Content.Form) -> Unit
) = Card (
    modifier = Modifier
        .padding(Constants.Dimens.dp4)
        .clickable(
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(Constants.Dimens.dp8),
        horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            val imgModel = File(role.image)
                .takeIf { role.image.isNotEmpty() && role.image != Constants.STR_SYSTEM && it.exists() }
                ?: R.drawable.app_icon_48x48px
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
                            color = if (formRoleSelection.fldSelectedRole == role) MaterialTheme.colorScheme.primary
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
    role : RoleEntity,
    formRoleSelection : FormRoleSelectionState,
    onFormEvent : (Events.Content.Form) -> Unit
) = Card(
    modifier = Modifier
        .padding(Constants.Dimens.dp8)
        .clickable(
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
                ?: R.drawable.app_icon_48x48px
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
    formRoleSelection: FormRoleSelectionState,
    onBottomBarEvent : (Events.BottomBar) -> Unit
) = BottomAppBar(
    modifier = Modifier.fillMaxWidth(),
    content = { Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        content = {
            IconButton(
                onClick = { onBottomBarEvent(Events.BottomBar.BtnRoleInfo.Clicked(formRoleSelection.fldSelectedRole)) },
                content = { Icon(imageVector = Icons.Default.Assistant, contentDescription = null) }
            )
            Button(
                onClick = { onBottomBarEvent(Events.BottomBar.BtnConfirmRole.Clicked(formRoleSelection.fldSelectedRole)) },
                content = { Text(stringResource(R.string.str_select)) },
                modifier = Modifier.weight(1f)
            )
        }
    ) }
)