package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize
import java.util.regex.Pattern

@Composable
fun TxtFieldEmail(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
    label: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    scrollState: ScrollState = rememberScrollState(),
    cursorBrush: Brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)),
    onTextLayout: (Density.(() -> TextLayoutResult?) -> Unit)? = null,
) {
    var isError by remember { mutableStateOf(false) }
    val validateEmail = { email: String ->
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        val pattern = Pattern.compile(emailRegex)
        isError = !pattern.matcher(email).matches()
    }
    val textFieldState = remember { TextFieldState(value) }
    Surface {
        BasicTextField(
            state = textFieldState,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            inputTransformation = inputTransformation,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            lineLimits = TextFieldLineLimits.SingleLine,
            onTextLayout = onTextLayout,
            cursorBrush = cursorBrush,
            scrollState = scrollState,
            decorator = { innerTextField ->
                EmailTextFieldDecorator(
                    innerTextField = innerTextField,
                    label = label,
                    errorMessage = if (isError) errorMessage else null,
                )
            }
        )
    }
    if (textFieldState.text != value) {
        onValueChange(textFieldState.text.toString())
        validateEmail(textFieldState.text.toString())
    }
}

@Composable
private fun EmailTextFieldDecorator(
    innerTextField: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    errorMessage: String?
) {
    Column {
        label?.invoke()
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (errorMessage != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(8.dp)
        ) { innerTextField() }
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTxtFieldEmail() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var email by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            val validateEmail = { email: String ->
                val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
                val pattern = Pattern.compile(emailRegex)
                errorMessage = if (!pattern.matcher(email).matches() && email.isNotEmpty()) {
                    "Invalid email address"
                } else {
                    null
                }
            }
            TxtFieldEmail(
                value = email,
                onValueChange = {
                    email = it
                    validateEmail(it)
                },
                label = { Text("Email") },
                errorMessage = errorMessage
            )
        }
    }
}