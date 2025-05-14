package com.thomas200593.mdm.features.introduction.initialization.ui

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.TxtMdTitle
import com.thomas200593.mdm.core.ui.component.dialog.ErrorDialog
import com.thomas200593.mdm.core.ui.component.dialog.LoadingDialog
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.dialog.SuccessDialog
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPersonName
import com.thomas200593.mdm.features.introduction.initialization.ui.events.Events
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ComponentsState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.FormInitializationState
import com.thomas200593.mdm.features.bootstrap.nav.navToBootstrap
import com.thomas200593.mdm.features.common.cnf_ui_contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.common.cnf_ui_font_size.entity.FontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization, vm: VMInitialization = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    /*val uiState by vm.uiState.collectAsStateWithLifecycle()
    val formInitialization = vm.formInitialization
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrInitialization(
        scrGraph = scrGraph, components = uiState.componentsState, formInitialization = formInitialization,
        onDialogEvent = { vm.onDialogEvent(it) }, onTopBarEvent = { vm.onTopBarEvent(it) },
        onFormEvent = { vm.onFormEvent(it) }, onBottomBarEvent = { vm.onBottomBarEvent(it) },
        onInitializationSuccess = { vm.onDialogEvent(Events.Dialog.SuccessDismissed)
            .also { coroutineScope.launch { stateApp.navController.navToBootstrap() } } }
    )*/
    ScrInitialization()
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun ScrInitialization() {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null)
                        }
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it).fillMaxSize(),
                content = {
                    item {
                        PanelCard(
                            modifier = Modifier.padding(Constants.Dimens.dp16),
                            colors = CardDefaults.cardColors().copy(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            title = {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(modifier = Modifier.wrapContentWidth(), imageVector = Icons.Default.Info, contentDescription = null)
                                        TxtMdTitle(modifier = Modifier.weight(1.0f), text = "Important")
                                    }
                                )
                            },
                            content = { TxtMdBody(modifier = Modifier.fillMaxWidth(), text = "First thing first, set up your account") }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier,
                content = {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {},
                        content = {
                            Text("Proceed")
                        }
                    )
                }
            )
        }
    )
}
@Preview @Composable private fun Preview() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = true,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue,
    content = {
        ScrInitialization()
    }
)
/*@Composable private fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization, components: ComponentsState, formInitialization: FormInitializationState,
    onTopBarEvent: (Events.TopBar) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit, onDialogEvent: (Events.Dialog) -> Unit,
    onInitializationSuccess: () -> Unit
) = when (components) {
    is ComponentsState.Loading -> ScrLoading()
    is ComponentsState.Loaded -> ScreenContent(
        components = components, scrGraph = scrGraph, formInitialization = formInitialization,
        onDialogEvent = onDialogEvent, onTopBarEvent = onTopBarEvent,
        onFormEvent = onFormEvent, onBottomBarEvent = onBottomBarEvent,
        onInitializationSuccess = onInitializationSuccess
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun ScreenContent(
    scrGraph: ScrGraphs.Initialization, components: ComponentsState.Loaded, formInitialization: FormInitializationState,
    onDialogEvent: (Events.Dialog) -> Unit, onTopBarEvent : (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit,
    onInitializationSuccess: () -> Unit
) {
    HandleDialogs(
        dialog = components.dialogState, scrGraph = scrGraph, onDialogEvent = onDialogEvent,
        onTopBarEvent = onTopBarEvent, onInitializationSuccess = onInitializationSuccess
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(onTopBarEvent = onTopBarEvent) },
        content = { SectionContent(paddingValues = it, formInitialization = formInitialization, onFormEvent = onFormEvent) },
        bottomBar = { AnimatedVisibility (
            visible = formInitialization.btnProceedVisible,
            enter = fadeIn() + slideInVertically(), exit = fadeOut() + slideOutVertically(),
            content = { SectionBottomBar(btnProceedEnabled = formInitialization.btnProceedEnabled, onBottomBarEvent = onBottomBarEvent) }
        ) }
    )
}
@Composable private fun HandleDialogs(
    dialog: DialogState, scrGraph: ScrGraphs.Initialization, onDialogEvent: (Events.Dialog) -> Unit,
    onTopBarEvent: (Events.TopBar) -> Unit, onInitializationSuccess: () -> Unit
) = when(dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        onDismissRequest = { onTopBarEvent(Events.TopBar.BtnScrDesc.Dismissed) },
        title = stringResource(scrGraph.title), description = stringResource(scrGraph.description)
    )
    is DialogState.LoadingDialog -> LoadingDialog()
    is DialogState.ErrorDialog -> ErrorDialog(
        onDismissRequest = { onDialogEvent(Events.Dialog.ErrorDismissed) },
        message = "Initialization Failed!"
    )
    is DialogState.SuccessDialog -> SuccessDialog(
        onDismissRequest = { onInitializationSuccess() }, message = "Initialization Success!"
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(onTopBarEvent: (Events.TopBar) -> Unit) = TopAppBar(
    title = {}, actions = { IconButton(
        onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
        content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
    ) }
)
@Composable private fun SectionContent(paddingValues: PaddingValues, formInitialization: FormInitializationState, onFormEvent: (Events.Content.Form) -> Unit) = Surface(
    modifier = Modifier.padding(paddingValues),
    content = { LazyColumn(
        modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
        verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
        content = {
            item { PartTitle() }
            item { PartForm(formInitialization = formInitialization, onFormEvent = onFormEvent) }
        }
    ) }
)
@Composable private fun PartTitle() = Row(
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
@Composable private fun PartForm(formInitialization: FormInitializationState, onFormEvent : (Events.Content.Form) -> Unit) = Card(
    modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.extraSmall,
    content = { Column(
        modifier = Modifier.padding(Constants.Dimens.dp16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp), content =  {
            TxtFieldPersonName(
                value = formInitialization.fldFirstName,
                onValueChange = { onFormEvent(Events.Content.Form.FirstNameChanged(it)) },
                enabled = formInitialization.fldFirstNameEnabled,
                isError = formInitialization.fldFirstNameError.isNotEmpty(),
                errorMessage = formInitialization.fldFirstNameError,
                label = stringResource(R.string.str_first_name),
                placeholder = stringResource(R.string.str_first_name)
            )
            TxtFieldPersonName(
                value = formInitialization.fldLastName,
                onValueChange = { onFormEvent(Events.Content.Form.LastNameChanged(it)) },
                enabled = formInitialization.fldLastNameEnabled,
                isError = formInitialization.fldLastNameError.isNotEmpty(),
                errorMessage = formInitialization.fldLastNameError,
                label = stringResource(R.string.str_last_name),
                placeholder = stringResource(R.string.str_last_name)
            )
            TxtFieldEmail(
                value = formInitialization.fldEmail,
                onValueChange = { onFormEvent(Events.Content.Form.EmailChanged(it)) },
                enabled = formInitialization.fldEmailEnabled,
                isError = formInitialization.fldEmailError.isNotEmpty(),
                errorMessage = formInitialization.fldEmailError
            )
            TxtFieldPassword(
                value = formInitialization.fldPassword,
                onValueChange = { onFormEvent(Events.Content.Form.PasswordChanged(it)) },
                enabled = formInitialization.fldPasswordEnabled,
                isError = formInitialization.fldPasswordError.isNotEmpty(),
                errorMessage = formInitialization.fldPasswordError
            )
        }
    ) }
)
@Composable private fun SectionBottomBar(btnProceedEnabled: Boolean, onBottomBarEvent: (Events.BottomBar) -> Unit) = BottomAppBar(
    content = { Button (
        modifier = Modifier.fillMaxWidth(),
        onClick = { onBottomBarEvent(Events.BottomBar.BtnProceedInit.Clicked) },
        enabled = btnProceedEnabled,
        shape = MaterialTheme.shapes.extraSmall,
        content = { Text(text = stringResource(R.string.str_proceed)) }
    ) }
)*/
