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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.features.initial.nav.navToInitial
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
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMInitialization.Events.OnOpenEvent) })
    ScrInitialization(
        scrDataState = uiState.scrDataState,
        onEmailValueChanged = { vm.onEvent(VMInitialization.Events.FormEvents.FldEmailValChanged(it)) },
        onPasswordValueChanged = { vm.onEvent(VMInitialization.Events.FormEvents.FldPasswordValChanged(it)) },
        onBtnProceedClicked = {
            vm.onEvent(VMInitialization.Events.FormEvents.BtnProceedOnClick)
                /*.also { coroutineScope.launch { stateApp.navController.navToInitial() } }*/
        }
    )
}

@Composable
private fun ScrInitialization(
    scrDataState: VMInitialization.ScrDataState,
    onEmailValueChanged: (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit,
    onBtnProceedClicked: () -> Unit
) = when (scrDataState) {
    is VMInitialization.ScrDataState.Loading -> ScrLoading()
    is VMInitialization.ScrDataState.Loaded -> ScreenContent(
        scrData = scrDataState.scrData,
        onEmailValueChanged = onEmailValueChanged,
        onPasswordValueChanged = onPasswordValueChanged,
        onBtnProceedClicked = onBtnProceedClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    scrData: VMInitialization.ScrData,
    onEmailValueChanged: (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit,
    onBtnProceedClicked: () -> Unit
) = Scaffold(
    modifier = Modifier.imePadding(),
    topBar = { SectionTopBar() },
    content = {
        SectionContent(
            paddingValues = it,
            form = scrData.form,
            onEmailValueChanged = onEmailValueChanged,
            onPasswordValueChanged = onPasswordValueChanged
        )
    },
    bottomBar = {
        AnimatedVisibility(
            visible = scrData.form.btnProceedVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            SectionBottomBar(
                onBtnProceedClicked = onBtnProceedClicked
            )
        }
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar() {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = {/*TODO*/},
                content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
            )
        }
    )
}

@Composable
private fun SectionContent(
    paddingValues: PaddingValues,
    form: VMInitialization.Form,
    onEmailValueChanged: (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit
) {
    Surface(
        modifier = Modifier.padding(paddingValues),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
                content = {
                    /* Welcome Message */
                    item { PartTitle() }
                    /* Form */
                    item {
                        PartForm(
                            form = form,
                            onEmailValueChanged = onEmailValueChanged,
                            onPasswordValueChanged = onPasswordValueChanged
                        )
                    }
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
private fun PartForm(
    form: VMInitialization.Form,
    onEmailValueChanged : (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        content = {
            Column(
                modifier = Modifier.padding(Constants.Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content =  {
                    val isEmailError by
                        remember(form.fldEmailError) { derivedStateOf { form.fldEmailError.isNotEmpty() } }
                    val isPasswordError by
                        remember(form.fldPasswordError) { derivedStateOf { form.fldPasswordError.isNotEmpty() } }

                    TxtFieldEmail(
                        state = rememberTextFieldState(form.fldEmail.toString()),
                        onValueChanged = { onEmailValueChanged(it) },
                        enabled = form.fldEmailEnabled,
                        isError = isEmailError,
                        errorMessage = form.fldEmailError
                    )
                    TxtFieldPassword(
                        state = rememberTextFieldState(form.fldPassword.toString()),
                        onValueChanged = { onPasswordValueChanged(it) },
                        enabled = form.fldPasswordEnabled,
                        isError = isPasswordError,
                        errorMessage = form.fldPasswordError
                    )
                }
            )
        }
    )
}

@Composable
private fun SectionBottomBar(
    onBtnProceedClicked: () -> Unit
) {
    BottomAppBar(
        content = {
            Button (
                modifier = Modifier.fillMaxWidth(),
                onClick = onBtnProceedClicked,
                shape = MaterialTheme.shapes.extraSmall,
                content = { Text(text = stringResource(R.string.str_proceed)) }
            )
        }
    )
}