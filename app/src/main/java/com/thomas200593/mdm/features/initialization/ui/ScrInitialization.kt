package com.thomas200593.mdm.features.initialization.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.Dialog
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPersonName
import com.thomas200593.mdm.features.initial.nav.navToInitial
import com.thomas200593.mdm.features.initialization.ui.events.Events
import com.thomas200593.mdm.features.initialization.ui.state.ComponentsState
import com.thomas200593.mdm.features.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.initialization.ui.state.FormState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization,
    vm: VMInitialization = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val formState = vm.formState
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvents(Events.Screen.OnOpen) })
    ScrInitialization(
        scrGraph = scrGraph, componentsState = uiState.componentsState,
        formState = formState,
        onTopAppBarEvents = vm::onTopAppBarEvents, onDialogEvents = vm::onDialogEvents,
        onFormEvents = vm::onFormEvents, onBottomBarEvents = vm::onBottomBarEvents,
        onSuccessInitialization = {
            vm.onDialogEvents(Events.Dialog.InitializationSuccessOnDismiss)
                .also { coroutineScope.launch { stateApp.navController.navToInitial() } }
        }
    )
}
@Composable
private fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization,
    componentsState: ComponentsState,
    formState: FormState,
    onTopAppBarEvents: (Events.TopAppBar) -> Unit,
    onBottomBarEvents: (Events.BottomAppBar) -> Unit,
    onFormEvents: (Events.Content.Form) -> Unit,
    onDialogEvents: (Events.Dialog) -> Unit,
    onSuccessInitialization: () -> Unit
) = when (componentsState) {
    is ComponentsState.Loading -> ScrLoading()
    is ComponentsState.Loaded -> ScreenContent(
        components = componentsState, scrGraph = scrGraph,
        formState = formState,
        onTopAppBarEvents = onTopAppBarEvents, onDialogEvents = onDialogEvents,
        onFormEvents = onFormEvents, onBottomBarEvents = onBottomBarEvents,
        onSuccessInitialization = onSuccessInitialization
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    scrGraph: ScrGraphs.Initialization,
    components: ComponentsState.Loaded,
    formState: FormState,
    onTopAppBarEvents : (Events.TopAppBar) -> Unit,
    onFormEvents: (Events.Content.Form) -> Unit,
    onDialogEvents: (Events.Dialog) -> Unit,
    onBottomBarEvents: (Events.BottomAppBar) -> Unit,
    onSuccessInitialization: () -> Unit
) {
    HandleDialogs(
        dialogState = components.dialogState,
        scrGraph = scrGraph,
        onTopAppBarEvents = onTopAppBarEvents,
        onDialogEvents = onDialogEvents,
        onSuccessInitialization = onSuccessInitialization
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(onTopAppBarEvents) },
        content = {
            SectionContent(
                paddingValues = it,
                formState = formState,
                onFormEvents = onFormEvents
            )
        },
        bottomBar = {
            AnimatedVisibility (
                visible = formState.btnProceedVisible,
                enter = fadeIn() + slideInVertically(), exit = fadeOut() + slideOutVertically()
            ) {
                SectionBottomBar(
                    btnProceedEnabled = formState.btnProceedEnabled,
                    onBottomBarEvents = onBottomBarEvents
                )
            }
        }
    )
}
@Composable
private fun HandleDialogs(
    dialogState: DialogState,
    scrGraph: ScrGraphs.Initialization,
    onTopAppBarEvents: (Events.TopAppBar) -> Unit,
    onDialogEvents: (Events.Dialog) -> Unit,
    onSuccessInitialization: () -> Unit
) {
    when(dialogState) {
        is DialogState.None -> Unit
        is DialogState.InfoScrDesc -> Dialog(
            onDismissRequest = { onTopAppBarEvents(Events.TopAppBar.BtnScrDesc.OnDismiss) },
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(stringResource(scrGraph.title)) },
            text = { Text(stringResource(scrGraph.description)) },
            confirmButton = {
                Button(
                    onClick = { onTopAppBarEvents(Events.TopAppBar.BtnScrDesc.OnDismiss) },
                    content = { Text(stringResource(R.string.str_back)) }
                )
            }
        )
        is DialogState.Error -> Dialog(
            onDismissRequest = { onDialogEvents(Events.Dialog.InitializationErrorOnDismiss) },
            icon = { Icon(Icons.Default.Close, null) },
            title = { Text(stringResource(R.string.str_error)) },
            text = { Text("Initialization Error!") },
            confirmButton = {
                Button(
                    onClick = { onDialogEvents(Events.Dialog.InitializationErrorOnDismiss) },
                    content = { Text(stringResource(R.string.str_back)) }
                )
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
        is DialogState.SuccessInitialization -> Dialog(
            onDismissRequest = { onSuccessInitialization() },
            icon = { Icon(Icons.Default.Check, null) },
            title = { Text(stringResource(R.string.str_success)) },
            text = { Text("Initialization Success!") },
            confirmButton = {
                Button(
                    onClick = { onSuccessInitialization() },
                    content = { Text(stringResource(R.string.str_finish)) }
                )
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar(onTopAppBarEvents: (Events.TopAppBar) -> Unit) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = { onTopAppBarEvents(Events.TopAppBar.BtnScrDesc.OnClick) },
                content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
            )
        }
    )
}
@Composable
private fun SectionContent(paddingValues: PaddingValues, formState: FormState, onFormEvents: (Events.Content.Form) -> Unit) {
    Surface(
        modifier = Modifier.padding(paddingValues),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
                content = {
                    item { PartTitle() }
                    item { PartForm(formState = formState, onFormEvents = onFormEvents) }
                }
            )
        }
    )
}
@Composable
private fun PartTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        content = {
            Row(
                modifier = Modifier.weight(0.1f),
                content = { Icon(imageVector = Icons.Default.Checklist, contentDescription = null) }
            )
            Row(
                modifier = Modifier.weight(0.9f),
                content = { TxtLgTitle(stringResource(R.string.str_initialization_title)) }
            )
        }
    )
}
@Composable
private fun PartForm(formState: FormState, onFormEvents : (Events.Content.Form) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        content = {
            Column(
                modifier = Modifier.padding(Constants.Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content =  {
                    TxtFieldPersonName(
                        value = formState.fldFirstName,
                        onValueChange = { onFormEvents(Events.Content.Form.FldValChgFirstName(it)) },
                        enabled = formState.fldFirstNameEnabled,
                        isError = formState.fldFirstNameError.isNotEmpty(),
                        errorMessage = formState.fldFirstNameError,
                        label = stringResource(R.string.str_first_name),
                        placeholder = stringResource(R.string.str_first_name)
                    )
                    TxtFieldPersonName(
                        value = formState.fldLastName,
                        onValueChange = { onFormEvents(Events.Content.Form.FldValChgLastName(it)) },
                        enabled = formState.fldLastNameEnabled,
                        isError = formState.fldLastNameError.isNotEmpty(),
                        errorMessage = formState.fldLastNameError,
                        label = stringResource(R.string.str_last_name),
                        placeholder = stringResource(R.string.str_last_name)
                    )
                    TxtFieldEmail(
                        value = formState.fldEmail,
                        onValueChange = { onFormEvents(Events.Content.Form.FldValChgEmail(it)) },
                        enabled = formState.fldEmailEnabled,
                        isError = formState.fldEmailError.isNotEmpty(),
                        errorMessage = formState.fldEmailError
                    )
                    TxtFieldPassword(
                        value = formState.fldPassword,
                        onValueChange = { onFormEvents(Events.Content.Form.FldValChgPassword(it)) },
                        enabled = formState.fldPasswordEnabled,
                        isError = formState.fldPasswordError.isNotEmpty(),
                        errorMessage = formState.fldPasswordError
                    )
                }
            )
        }
    )
}
@Composable
private fun SectionBottomBar(btnProceedEnabled: Boolean, onBottomBarEvents: (Events.BottomAppBar) -> Unit) {
    BottomAppBar(
        content = {
            Button (
                modifier = Modifier.fillMaxWidth(),
                onClick = { onBottomBarEvents(Events.BottomAppBar.BtnProceedInit.OnClick) },
                enabled = btnProceedEnabled,
                shape = MaterialTheme.shapes.extraSmall,
                content = { Text(text = stringResource(R.string.str_proceed)) }
            )
        }
    )
}