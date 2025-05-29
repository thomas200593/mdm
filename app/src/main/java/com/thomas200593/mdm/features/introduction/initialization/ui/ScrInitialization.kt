package com.thomas200593.mdm.features.introduction.initialization.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.TxtMdTitle
import com.thomas200593.mdm.core.ui.common.anim.SlideUpFadeAnim
import com.thomas200593.mdm.core.ui.component.checkbox.HorizontalCheckbox
import com.thomas200593.mdm.core.ui.component.dialog.ErrorDialog
import com.thomas200593.mdm.core.ui.component.dialog.LoadingDialog
import com.thomas200593.mdm.core.ui.component.dialog.ScrInfoDialog
import com.thomas200593.mdm.core.ui.component.dialog.SuccessDialog
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldDatePicker
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPersonName
import com.thomas200593.mdm.features.bootstrap.nav.navToBootstrap
import com.thomas200593.mdm.features.introduction.initialization.ui.events.Events
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ScreenDataState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.DialogState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.FormInitializationState
import com.thomas200593.mdm.features.introduction.initialization.ui.state.ResultInitializationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization, vm: VMInitialization = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val formInitialization = vm.formInitialization
    LaunchedEffect (key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrInitialization(
        scrGraph = scrGraph,
        uiState = uiState,
        formInitialization = formInitialization,
        onDialogEvent = { when (it) {
            is Events.Dialog.SuccessDismissed -> vm.onDialogEvent(it)
                .also { coroutineScope.launch { stateApp.navController.navToBootstrap() } }
            else -> vm.onDialogEvent(it)
        } },
        onTopBarEvent = { vm.onTopBarEvent(it) },
        onFormEvent = { vm.onFormEvent(it) },
        onBottomBarEvent = { vm.onBottomBarEvent(it) }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun ScrInitialization(
    scrGraph: ScrGraphs.Initialization, uiState: VMInitialization.UiState, formInitialization: FormInitializationState,
    onDialogEvent: (Events.Dialog) -> Unit, onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit
) = when (uiState.screenData) {
    is ScreenDataState.Loading -> ScrLoading()
    is ScreenDataState.Loaded -> ScreenContent(
        scrGraph = scrGraph,
        resultInitialization = uiState.resultInitialization,
        dialog = uiState.dialog,
        formInitialization = formInitialization,
        onDialogEvent = onDialogEvent,
        onTopBarEvent = onTopBarEvent,
        onFormEvent = onFormEvent,
        onBottomBarEvent = onBottomBarEvent
    )
}
@Composable private fun HandleDialogs(
    scrGraph: ScrGraphs.Initialization, dialog: DialogState, resultInitialization: ResultInitializationState,
    onTopBarEvent: (Events.TopBar) -> Unit, onDialogEvent: (Events.Dialog) -> Unit
) = when(dialog) {
    is DialogState.None -> Unit
    is DialogState.ScrDescDialog -> ScrInfoDialog(
        title = stringResource(scrGraph.title), description = stringResource(scrGraph.description),
        onDismissRequest = { onTopBarEvent(Events.TopBar.BtnScrDesc.Dismissed) }
    )
    is DialogState.LoadingDialog -> LoadingDialog()
    is DialogState.ErrorDialog -> when (resultInitialization) {
        is ResultInitializationState.Idle, is ResultInitializationState.Loading, is ResultInitializationState.Success -> Unit
        is ResultInitializationState.Failure -> ErrorDialog(
            message = "Initialization Failed!", error = resultInitialization.t,
            onDismissRequest = { onDialogEvent(Events.Dialog.ErrorDismissed) }
        )
    }
    is DialogState.SuccessDialog -> SuccessDialog(
        message = "Initialization Success!", onDismissRequest = { onDialogEvent(Events.Dialog.SuccessDismissed) }
    )
}
@Composable private fun ScreenContent(
    scrGraph: ScrGraphs.Initialization, dialog: DialogState,
    formInitialization: FormInitializationState, resultInitialization: ResultInitializationState,
    onDialogEvent: (Events.Dialog) -> Unit, onTopBarEvent: (Events.TopBar) -> Unit,
    onFormEvent: (Events.Content.Form) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit
) {
    HandleDialogs(
        scrGraph = scrGraph, dialog = dialog, resultInitialization = resultInitialization,
        onTopBarEvent = onTopBarEvent, onDialogEvent = onDialogEvent
    )
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { SectionTopBar(onTopBarEvent = onTopBarEvent) },
        content = { SectionContent(paddingValues = it, formInitialization = formInitialization, onFormEvent = onFormEvent) },
        bottomBar = { SlideUpFadeAnim(
            visible = formInitialization.btnProceedVisible,
            content = { SectionBottomBar(
                formInitialization = formInitialization, onBottomBarEvent = onBottomBarEvent
            ) }
        ) }
    )
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionTopBar(
    onTopBarEvent: (Events.TopBar) -> Unit
) = TopAppBar(
    title = {},
    actions = {
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
            content = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            }
        )
    }
)
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SectionContent(
    paddingValues: PaddingValues, formInitialization: FormInitializationState,
    onFormEvent: (Events.Content.Form) -> Unit
) = Surface(
    modifier = Modifier.padding(paddingValues).fillMaxSize(),
    content =  { LazyColumn(
        modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
        verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
        content = {
            item { PartFormTitle() }
            item { PartForm(
                formInitialization = formInitialization,
                onFormEvent = onFormEvent
            ) }
        }
    ) }
)
@Composable private fun PartFormTitle() = PanelCard(
    modifier = Modifier.padding(Constants.Dimens.dp8),
    colors = CardDefaults.cardColors().copy(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ),
    title = { Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(modifier = Modifier.wrapContentWidth(), imageVector = Icons.Default.Info, contentDescription = null)
            TxtMdTitle(modifier = Modifier.weight(1.0f), text = "Important")
        }
    ) },
    content = { TxtMdBody(modifier = Modifier.fillMaxWidth(), text = "First thing first, set up your account") }
)
@Composable private fun partTOCText(): AnnotatedString = buildAnnotatedString {
    append("I agree to the "); withLink(
        link = LinkAnnotation.Url(
            url = "https://google.com/",
            styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold))
        ),
        block = { append("Terms and Conditions") }
    ); append(" and "); withLink(
        link = LinkAnnotation.Url(
            url = "https://google.com/",
            styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold))
        ),
        block = { append("Privacy Policy") }
    ); append(".")
}
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun PartForm(
    formInitialization: FormInitializationState, onFormEvent: (Events.Content.Form) -> Unit
) = PanelCard(modifier = Modifier.padding(Constants.Dimens.dp8), title = { TxtLgTitle(stringResource(R.string.str_initialization)) },
    content = {
        TxtFieldPersonName(
            value = formInitialization.fldFirstName,
            onValueChange = { onFormEvent(Events.Content.Form.FirstNameChanged(it)) },
            enabled = formInitialization.fldFirstNameEnabled,
            isError = formInitialization.fldFirstNameError.isNotEmpty(),
            errorMessage = formInitialization.fldFirstNameError,
            label = "First name", placeholder = "John"
        )
        TxtFieldPersonName(
            value = formInitialization.fldLastName,
            onValueChange = { onFormEvent(Events.Content.Form.LastNameChanged(it)) },
            enabled = formInitialization.fldLastNameEnabled,
            isError = formInitialization.fldLastNameError.isNotEmpty(),
            errorMessage = formInitialization.fldLastNameError,
            label = "Last name (optional)", placeholder = "Doe"
        )
        TxtFieldDatePicker(
            value = formInitialization.fldDateOfBirth,
            onValueChange = { onFormEvent(Events.Content.Form.DateOfBirthChanged(it)) },
            enabled = formInitialization.fldDateOfBirthEnabled,
            isError = formInitialization.fldDateOfBirthError.isNotEmpty(),
            errorMessage = formInitialization.fldDateOfBirthError,
            label = "Date of birth", placeholder = "YYYY-MM-DD"
        )
        TxtFieldEmail(
            value = formInitialization.fldEmail,
            onValueChange = { onFormEvent(Events.Content.Form.EmailChanged(it)) },
            enabled = formInitialization.fldEmailEnabled,
            isError = formInitialization.fldEmailError.isNotEmpty(),
            errorMessage = formInitialization.fldEmailError,
            placeholder = "john.doe@email.com"
        )
        TxtFieldPassword(
            value = formInitialization.fldPassword,
            onValueChange = { onFormEvent(Events.Content.Form.PasswordChanged(it)) },
            enabled = formInitialization.fldPasswordEnabled,
            isError = formInitialization.fldPasswordError.isNotEmpty(),
            errorMessage = formInitialization.fldPasswordError,
            placeholder = "********"
        )
        HorizontalCheckbox(
            annotatedText = partTOCText(),
            enabled = formInitialization.fldChbToCEnabled,
            checked = formInitialization.fldChbToCChecked,
            onCheckedChange = { onFormEvent(Events.Content.Form.CheckBoxChanged(it)) }
        )
    }
)
@Composable private fun SectionBottomBar(
    formInitialization: FormInitializationState,
    onBottomBarEvent: (Events.BottomBar) -> Unit
) = BottomAppBar( content = { Button (
    modifier = Modifier.fillMaxWidth(),
    onClick = { onBottomBarEvent(Events.BottomBar.BtnProceedInit.Clicked) },
    enabled = formInitialization.btnProceedEnabled,
    shape = MaterialTheme.shapes.extraSmall,
    content = { Text(text = stringResource(R.string.str_proceed)) }
) } )