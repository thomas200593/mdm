package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

@Composable fun TxtFieldPersonName(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Outlined.Face, contentDescription = null) }
) {
    val context = LocalContext.current
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    val errorTexts by remember(context, errorMessage)
    { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    OutlinedTextField(
        value = value,
        onValueChange = { currentOnValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        label = { label?.let { Text(it) } },
        placeholder = { placeholder?.let { Text(it) } },
        leadingIcon = leadingIcon,
        supportingText = { if (isError && errorTexts.isNotEmpty()) ErrorSupportingText(errorTexts) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        singleLine = true
    )
}