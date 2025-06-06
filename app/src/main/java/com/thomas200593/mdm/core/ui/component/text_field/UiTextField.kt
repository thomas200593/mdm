package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.core.ui.component.text.TextType
import com.thomas200593.mdm.core.ui.component.text.UiText
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

@Composable fun UiTextField(
    value : String,
    onValueChange : (String) -> Unit,
    modifier : Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    errorMessage: List<UiText> = emptyList(),
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    val errorTexts by remember (context, errorMessage) { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    Column (modifier = modifier.fillMaxWidth()) {
        // Label
        if (!label.isNullOrBlank()) {
            UiText(
                text = label,
                type = TextType.LABEL_MD,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Surface (
            shape = MaterialTheme.shapes.extraSmall,
            border = BorderStroke(1.dp, if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline),
            tonalElevation = 0.dp
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                leadingIcon?.invoke()
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        Box(Modifier.padding(start = 8.dp)) {
                            if (value.isEmpty() && !placeholder.isNullOrBlank()) {
                                UiText(
                                    text = placeholder,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (isError && errorTexts.isNotEmpty()) {
            ErrorSupportingText(errorTexts)
        }
    }
}
@Preview @Composable private fun Preview() {
    var value by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        UiTextField(
            value = value,
            onValueChange = { value = it }
        )
    }
}