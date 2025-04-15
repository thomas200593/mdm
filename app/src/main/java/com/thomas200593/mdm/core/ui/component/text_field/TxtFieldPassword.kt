package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun TxtFieldPassword(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList()
) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        enabled = enabled,
        readOnly = readOnly,
        label = { Text(stringResource(R.string.str_password)) },
        placeholder = { Text(stringResource(R.string.str_password)) },
        leadingIcon = { Icon(Icons.Outlined.Password, null) },
        trailingIcon = {
            PasswordVisibilityToggleIcon(
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = { if (isError && errorMessage.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                content = { errorMessage.forEach { Text("• ${it.asString(context)}") } }
            )
        } },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
    )
}

@Composable
fun PasswordVisibilityToggleIcon(
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit
) {
    // Determine the icon based on password visibility
    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val contentDescription = if (passwordVisible) "Hide password icon" else "Show password icon"

    // IconButton to toggle password visibility
    IconButton (onClick = onTogglePasswordVisibility) {
        Icon(imageVector = image, contentDescription = contentDescription)
    }
}