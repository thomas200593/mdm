package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.setSelectedDate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.ui.platform.LocalConfiguration

enum class DateTimePattern (val pattern: String) { YYYY_MM_DD("yyyy-MM-dd") }

@OptIn(ExperimentalMaterial3Api::class) @Composable fun TxtFieldDatePicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Default.DateRange, contentDescription = null) },
    minYear: Int = -120,
    maxYear: Int = 0,
    internalFormat: DateTimePattern = DateTimePattern.YYYY_MM_DD,
    displayFormat: DateTimePattern = DateTimePattern.YYYY_MM_DD,
    datePickerDisplayMode: DisplayMode = DisplayMode.Picker,
    locale: Locale = LocalConfiguration.current.locales[0]
) {
    val context = LocalContext.current
    val errorTexts by remember(context, errorMessage) { derivedStateOf { errorMessage.map { "• ${it.asString(context)}" } } }
    val internalFormatter = remember (internalFormat) { DateTimeFormatter.ofPattern(internalFormat.pattern).withLocale(Locale.US) }
    val displayFormatter = remember(displayFormat, locale) {
        runCatching { DateTimeFormatter.ofPattern(displayFormat.pattern, locale) }
            .getOrElse { DateTimeFormatter.ofPattern(DateTimePattern.YYYY_MM_DD.pattern, Locale.US) }
    }
    val today = remember { LocalDate.now() }
    val todayFormatted = remember(internalFormatter) { today.format(internalFormatter) }
    var lastConfirmedDate by remember { mutableStateOf(value.ifBlank { todayFormatted }) }
    val minDate = remember(minYear) { minYear.let { today.plusYears(it.toLong()) } }
    val maxDate = remember(maxYear) { today.plusYears(maxYear.toLong()) }
    var showDialog by remember { mutableStateOf(false) }
    val initialDate = remember(value) { runCatching { LocalDate.parse(value, internalFormatter) }.getOrElse { today } }
    val initialMillis = remember(initialDate) { initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() }
    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val date = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate()
            return !date.isBefore(minDate) && !date.isAfter(maxDate)
        }
    }
    val yearRange = remember(minDate, maxDate) { IntRange(minDate.year, maxDate.year) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis, yearRange = yearRange,
        selectableDates = selectableDates, initialDisplayMode = datePickerDisplayMode
    )
    // Sync picker selection when dialog shows or lastConfirmedDate changes
    LaunchedEffect (showDialog, lastConfirmedDate) {
        if (showDialog) {
            val date = runCatching {
                LocalDate.parse(lastConfirmedDate, internalFormatter)
            }.getOrNull() ?: today
            datePickerState.setSelectedDate(date)
        }
    }
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = {
                onValueChange(lastConfirmedDate)
                showDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selected = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate().format(internalFormatter)
                            lastConfirmedDate = selected
                            onValueChange(selected)
                        }
                        showDialog = false
                    },
                    content = { Text("OK") }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onValueChange(lastConfirmedDate)
                        showDialog = false
                    },
                    content = { Text("Cancel") }
                )
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
                    DatePicker(state = datePickerState)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = {
                                lastConfirmedDate = todayFormatted
                                onValueChange(todayFormatted)
                                showDialog = false
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text("Reset to Today")
                        }
                    }
                }
            }
        )
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            val displayText = runCatching { LocalDate.parse(value, internalFormatter).format(displayFormatter) }
                .getOrDefault(Constants.STR_EMPTY)
            OutlinedTextField(
                value = displayText,
                onValueChange = {},
                modifier = Modifier.weight(1f),
                enabled = true,
                readOnly = true,
                label = { label?.let { Text(it) } },
                placeholder = { placeholder?.let { Text(it) } },
                leadingIcon = leadingIcon,
                trailingIcon = {
                    if (value.isNotBlank()) IconButton(
                        onClick = {
                            lastConfirmedDate = Constants.STR_EMPTY
                            onValueChange(Constants.STR_EMPTY)
                        },
                        enabled = enabled,
                        content =  { Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Date",
                            tint = MaterialTheme.colorScheme.error
                        ) }
                    )
                },
                supportingText = { if (isError && errorTexts.isNotEmpty()) { ErrorSupportingText(errorTexts) } },
                isError = isError,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true
            )
            IconButton(
                onClick = { showDialog = true },
                enabled = enabled,
                shape = MaterialTheme.shapes.extraSmall,
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                content = { Icon(Icons.Default.Edit, contentDescription = null) }
            )
        }
    )
}