package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TxtFieldPersonName(
    modifier: Modifier = Modifier,
    state: TextFieldState = rememberTextFieldState(),
    onValueChanged: (CharSequence) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Outlined.Face, contentDescription = null) } // Default icon
) {
    val context = LocalContext.current

    // Listen for text state changes
    LaunchedEffect(state) {
        snapshotFlow { state.text }.collectLatest { newValue ->
            onValueChanged(
                newValue
            )
        }
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        state = state,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Companion.Text,
            imeAction = ImeAction.Companion.Next
        ),
        label = { label?.let { Text(it) } },
        placeholder = { placeholder?.let { Text(it) } },
        leadingIcon = { leadingIcon() },
        supportingText = {
            if (isError && errorMessage.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8)
                ) {
                    errorMessage.forEach { Text("• ${it.asString(context)}") }
                }
            }
        },
        isError = isError,
        lineLimits = TextFieldLineLimits.SingleLine
    )
}