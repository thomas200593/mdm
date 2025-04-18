package com.thomas200593.mdm.features.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.features.auth.ui.events.Events
import com.thomas200593.mdm.features.auth.ui.state.ComponentsState
import com.thomas200593.mdm.features.auth.ui.state.FormAuthState
import kotlinx.coroutines.CoroutineScope

@Composable
fun ScrAuth(
    scrGraph: ScrGraphs.Auth, vm: VMAuth = hiltViewModel(), stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val formAuth = vm.formAuth
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvent(Events.Screen.Opened) })
    ScrAuth(
        scrGraph = scrGraph, components = uiState.componentsState,
        formAuth = formAuth,
        onTopBarEvent = vm::onTopBarEvent,
        onFormAuthEvent = vm::onFormAuthEvent
    )
}
@Composable
private fun ScrAuth(
    scrGraph: ScrGraphs.Auth, components: ComponentsState, formAuth: FormAuthState,
    onTopBarEvent: (Events.TopBar) -> Unit, onFormAuthEvent: (Events.Content.Form) -> Unit
) = when (components) {
    is ComponentsState.Loading -> ScrLoading()
    is ComponentsState.Loaded -> ScreenContent(
        scrGraph = scrGraph, components = components, formAuth = formAuth,
        onTopBarEvent = onTopBarEvent,
        onFormAuthEvent = onFormAuthEvent
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    scrGraph: ScrGraphs.Auth,
    components: ComponentsState.Loaded,
    formAuth: FormAuthState,
    onTopBarEvent: (Events.TopBar) -> Unit,
    onFormAuthEvent: (Events.Content.Form) -> Unit
) {
    HandleDialogs()
    Scaffold(
        topBar = { SectionTopBar(onTopBarEvent = onTopBarEvent) },
        content = { SectionContent(
            paddingValues = it, formAuth = formAuth,
            onFormAuthEvent = onFormAuthEvent
        ) },
        bottomBar = { SectionBottomBar() }
    )
}
@Composable
private fun HandleDialogs() {}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar(onTopBarEvent: (Events.TopBar) -> Unit) = TopAppBar(
    title = {},
    actions = {
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnSetting.Clicked) },
            content = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) }
        )
        IconButton(
            onClick = { onTopBarEvent(Events.TopBar.BtnScrDesc.Clicked) },
            content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
    }
)
@Composable
private fun SectionContent(
    paddingValues: PaddingValues, formAuth: FormAuthState, onFormAuthEvent: (Events.Content.Form) -> Unit
) = Surface(
    modifier = Modifier.padding(paddingValues),
    content = {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
            content = {
                item { SectionPageLogo() }
                item { SectionPageTitle() }
                item { SectionPageAuthPanel(
                    formAuth = formAuth,
                    onFormAuthEvent = onFormAuthEvent
                ) }
            }
        )
    }
)
@Composable
private fun SectionPageLogo() {
    Surface(
        modifier = Modifier.height(100.dp),
        content = {
            Image(
                painter = painterResource(R.drawable.app_icon_48x48px),
                contentDescription = null
            )
        }
    )
}
@Composable
private fun SectionPageTitle() = Column (
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16, Alignment.CenterVertically),
    content = {
        TxtLgTitle(stringResource(R.string.str_auth))
        TxtMdBody("To continue, please sign-in into your account.")
    }
)
@Composable
private fun SectionPageAuthPanel(
    onFormAuthEvent: (Events.Content.Form) -> Unit,
    formAuth: FormAuthState
) = PanelCard(
    modifier = Modifier.padding(Constants.Dimens.dp16),
    content = {
        TxtFieldEmail(
            value = formAuth.fldEmail,
            onValueChange = { onFormAuthEvent(Events.Content.Form.EmailChanged(it)) },
            enabled = formAuth.fldEmailEnabled
        )
        TxtFieldPassword(
            value = formAuth.fldPassword,
            onValueChange = { onFormAuthEvent(Events.Content.Form.PasswordChanged(it)) },
            enabled = formAuth.fldPasswordEnabled
        )
        PanelCard(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            content = { TxtMdBody("ErrorDialog") }
        )
        Button (
            modifier = Modifier.fillMaxWidth(),
            onClick = { onFormAuthEvent(Events.Content.Form.BtnSignIn.Clicked) },
            shape = MaterialTheme.shapes.extraSmall,
            enabled = formAuth.btnSignInEnabled,
            content = { Text(text = "Sign in") }
        )
        SectionRecoverAccount(onFormAuthEvent = onFormAuthEvent)
    }
)
@Composable
private fun SectionRecoverAccount(onFormAuthEvent : (Events.Content.Form) -> Unit) = Column (
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = { TextButton(
        onClick = { onFormAuthEvent(Events.Content.Form.BtnRecoverAccount.Clicked) },
        content = { TxtMdBody("Recover my account") }
    ) }
)
@Composable
private fun SectionBottomBar() = BottomAppBar (
    containerColor = MaterialTheme.colorScheme.surface,
    content = { Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = { TxtMdBody(stringResource(R.string.app_name) + Constants.STR_APP_VERSION) }
    ) }
)