package com.thomas200593.mdm.core.ui.component.text_field

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

@Composable fun TxtFieldPassword(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList()
) {
    val context = LocalContext.current
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    var passwordVisible by remember { mutableStateOf(false) }
    val errorTexts by remember(context, errorMessage)
    { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { currentOnValueChange(it) },
        enabled = enabled,
        readOnly = readOnly,
        label = { Text(stringResource(R.string.str_password)) },
        placeholder = { Text(stringResource(R.string.str_password)) },
        leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = null) },
        trailingIcon = { PasswordVisibilityToggleIcon(
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
        ) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = { if (isError && errorTexts.isNotEmpty()) ErrorSupportingText(errorTexts) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true
    )
}
@Composable private fun PasswordVisibilityToggleIcon(
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit
) {
    val image = remember(passwordVisible)
    { if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff }
    val contentDescription = if (passwordVisible) "Hide password icon" else "Show password icon"
    IconButton(
        onClick = onTogglePasswordVisibility,
        content = { Icon(imageVector = image, contentDescription = contentDescription) }
    )
}