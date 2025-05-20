package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

@Composable fun TxtFieldEmail(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String = stringResource(R.string.str_email),
    placeholder: String = stringResource(R.string.str_email),
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Outlined.Email, null) }
) {
    val context = LocalContext.current
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    val errorTexts by remember (context, errorMessage)
    { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { currentOnValueChange(it) },
        enabled = enabled,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon,
        supportingText = { if (isError && errorTexts.isNotEmpty()) ErrorSupportingText(errorTexts) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
        singleLine = true
    )
}