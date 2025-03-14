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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.BtnConfLang
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.features.initialization.entity.InitializationScrData
import kotlinx.coroutines.CoroutineScope

@Composable
fun ScrInitialization(
    vm: VMInitialization = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    stateApp: StateApp = LocalStateApp.current
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMInitialization.Ui.Events.OnOpenEvent) })
    ScrInitialization(
        dataState = uiState.dataState,
        onEmailValueChanged = { vm.onEvent(VMInitialization.Ui.Events.FormEvent.EmailValueChanged(it)) },
        onPasswordValueChanged = { vm.onEvent(VMInitialization.Ui.Events.FormEvent.PasswordValueChanged(it)) },
        onBtnProceedClicked = { /*TODO*/ }
    )
}

@Composable
private fun ScrInitialization(
    dataState: VMInitialization.Ui.DataState,
    onEmailValueChanged : (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit,
    onBtnProceedClicked: () -> Unit
) = when (dataState) {
    VMInitialization.Ui.DataState.Loading -> ScrLoading()
    is VMInitialization.Ui.DataState.Loaded -> ScreenContent(
        data = dataState.data,
        onEmailValueChanged = onEmailValueChanged,
        onPasswordValueChanged = onPasswordValueChanged,
        onBtnProceedClicked = onBtnProceedClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    data: InitializationScrData,
    onEmailValueChanged : (CharSequence) -> Unit,
    onPasswordValueChanged: (CharSequence) -> Unit,
    onBtnProceedClicked: () -> Unit
) = Scaffold(
    modifier = Modifier.imePadding(), // Add this to push everything above the keyboard
    topBar = { SectionTopBar() },
    content = {
        SectionContent(
            paddingValues = it,
            formData = data.formData,
            onEmailValueChanged = onEmailValueChanged,
            onPasswordValueChanged = onPasswordValueChanged
        )
    },
    bottomBar = {
        AnimatedVisibility(
            visible = data.formData.btnProceedEnabled,
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
            BtnConfLang(onClick = {/*TODO*/}, border = null)
            IconButton(onClick = {/*TODO*/}, content = {
                Icon(imageVector = Icons.Default.Info, contentDescription = null) }
            )
        }
    )
}

@Composable
private fun SectionContent(
    paddingValues: PaddingValues,
    formData: VMInitialization.Ui.FormData,
    onEmailValueChanged : (CharSequence) -> Unit,
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
                            formData = formData,
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
                content = { TxtLgTitle("First thing first, set up your admin account.") }
            )
        }
    )
}

@Composable
private fun PartForm(
    formData: VMInitialization.Ui.FormData,
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
                    TxtFieldEmail(
                        state = rememberTextFieldState(formData.email.toString()),
                        onValueChanged = { onEmailValueChanged(it) },
                        isError = formData.emailError.isNotEmpty(),
                        errorMessage = formData.emailError
                    )
                    TxtFieldPassword(
                        state = rememberTextFieldState(formData.password.toString()),
                        onValueChanged = { onPasswordValueChanged(it) },
                        isError = formData.passwordError.isNotEmpty(),
                        errorMessage = formData.passwordError
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