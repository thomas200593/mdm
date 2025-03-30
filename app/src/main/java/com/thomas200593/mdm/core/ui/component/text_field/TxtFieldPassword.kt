package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun TxtFieldPassword(
    modifier: Modifier = Modifier,
    state: TextFieldState = rememberTextFieldState(),
    onValueChanged: (CharSequence) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList()
) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) } // Toggle password visibility
    // Listen for text state changes
    LaunchedEffect(
        key1 = state.text,
        block = { snapshotFlow { state.text }.debounce(400).collectLatest { newValue -> onValueChanged(newValue) } }
    )
    OutlinedSecureTextField(
        modifier = modifier.fillMaxWidth(),
        state = state,
        enabled = enabled,
        isError = isError,
        label = { Text(stringResource(R.string.str_password)) },
        placeholder = { Text(stringResource(R.string.str_password)) },
        leadingIcon = { Icon(Icons.Outlined.Password, null) },
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                content = {
                    Icon(
                        imageVector =
                            if (passwordVisible) Icons.Outlined.Visibility
                            else Icons.Outlined.VisibilityOff,
                        contentDescription = null
                    )
                }
            )
        },
        supportingText = {
            if (isError && errorMessage.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                    content = { errorMessage.forEach { Text("• ${it.asString(context)}") } }
                )
            }
        },
        textObfuscationMode = if(passwordVisible) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped
    )
}