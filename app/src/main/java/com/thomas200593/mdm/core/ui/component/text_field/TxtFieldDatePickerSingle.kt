package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import com.thomas200593.mdm.core.design_system.util.toEpochMilli
import com.thomas200593.mdm.core.design_system.util.toLocalDate
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class) @Composable fun TxtFieldDatePickerSingle(
    modifier: Modifier = Modifier,
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String = "Date Picker",
    placeholder: String = "Pick a date"
) {
    val context = LocalContext.current
    val currentOnValueChange by rememberUpdatedState(onValueChange)
    val errorTexts by remember (context, errorMessage)
    { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    val today = remember { LocalDate.now() }
    val hundredYearsAgo = remember { today.minusYears(100) }
    var showDialog by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().clickable(enabled && !readOnly) { showDialog = true },
        value = value.format(dateFormatter),
        onValueChange = {},
        enabled = enabled,
        readOnly = true,
        isError = isError,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(Icons.Default.DateRange, null) },
        supportingText = { if (isError && errorTexts.isNotEmpty()) ErrorSupportingText(errorTexts) },
        singleLine = true
    )
    if (showDialog) {
        val datePickerState = rememberDatePickerState (
            initialSelectedDateMillis = value.toEpochMilli(),
            yearRange = hundredYearsAgo.year..today.year
        )
        DatePickerDialog (
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis
                            ?. let { millis -> currentOnValueChange(millis.toLocalDate()) }
                        showDialog = false
                    },
                    content = { Text(stringResource(android.R.string.ok)) })
            },
            dismissButton = {
                TextButton (
                    onClick = { showDialog = false },
                    content = { Text(stringResource(android.R.string.cancel)) }
                )
            },
            content = { DatePicker(state = datePickerState, showModeToggle = false) }
        )
    }
}