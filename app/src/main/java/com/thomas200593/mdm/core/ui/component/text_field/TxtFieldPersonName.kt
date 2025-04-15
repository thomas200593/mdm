package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun TxtFieldPersonName(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Outlined.Face, contentDescription = null) }
) {
    val context = LocalContext.current
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        label = { label?.let { Text(it) } },
        placeholder = { placeholder?.let { Text(it) } },
        leadingIcon = { leadingIcon() },
        supportingText = { if (isError && errorMessage.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                content = { errorMessage.forEach { Text("• ${it.asString(context)}") } }
            )
        } },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        singleLine = true
    )
}