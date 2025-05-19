package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TxtFieldDatePicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Default.DateRange, contentDescription = null) }
) {
    val context = LocalContext.current
    val errorTexts by remember (context, errorMessage)
    { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        label = { label?.let { Text(it) } },
        placeholder = { placeholder?.let { Text(it) } },
        leadingIcon = leadingIcon,
        supportingText = { if(isError && errorTexts.isNotEmpty()) ErrorSupportingText(errorTexts) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        singleLine = true
    )
}