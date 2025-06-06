package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.setSelectedDate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.DateTimePattern
import com.thomas200593.mdm.core.ui.component.text.UiText
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class) @Composable fun TxtFieldDatePicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: List<UiText> = emptyList(),
    leadingIcon: @Composable (() -> Unit) = { Icon(Icons.Default.DateRange, contentDescription = null) },
    label: String? = null,
    placeholder: String? = null,
    minYear: Int = -120,
    maxYear: Int = 0,
    internalFormat: DateTimePattern = DateTimePattern.YYYY_MM_DD,
    displayFormat: DateTimePattern = DateTimePattern.YYYY_MM_DD,
    datePickerDisplayMode: DisplayMode = DisplayMode.Picker,
    locale: Locale = LocalConfiguration.current.locales[0]
) {
    val today = remember { Constants.NOW_DATE }
    val internalFormatter = remember(internalFormat) { DateTimeFormatter.ofPattern(internalFormat.pattern).withLocale(Locale.US) }
    val displayFormatter = remember(displayFormat, locale) { runCatching { DateTimeFormatter.ofPattern(displayFormat.pattern, locale) }
        .getOrElse { DateTimeFormatter.ofPattern(DateTimePattern.YYYY_MM_DD.pattern, Locale.US) } }
    val displayText = runCatching { LocalDate.parse(value, internalFormatter).format(displayFormatter) }
        .getOrDefault(Constants.STR_EMPTY)
    val parsedDate = remember(value) { runCatching { LocalDate.parse(value, internalFormatter) }.getOrNull() ?: today }
    val minDate = remember(minYear) { today.plusYears(minYear.toLong()) }
    val maxDate = remember(maxYear) { today.plusYears(maxYear.toLong()) }
    val yearRange = remember(minDate, maxDate) { IntRange(minDate.year, maxDate.year) }
    val selectableDates = object : SelectableDates { override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val date = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        return !date.isBefore(minDate) && !date.isAfter(maxDate)
    } }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        yearRange = yearRange, selectableDates = selectableDates, initialDisplayMode = datePickerDisplayMode
    )
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = showDialog, key2 = value, block = { if (showDialog) {
        val newMillis = parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (datePickerState.selectedDateMillis != newMillis) datePickerState.setSelectedDate(parsedDate)
    } })
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                                .toLocalDate().format(internalFormatter)
                            onValueChange(selectedDate)
                        }
                        showDialog = false
                    },
                    content = {
                        UiText(stringResource(R.string.str_ok))
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false },
                    content = { UiText(stringResource(R.string.str_back)) }
                )
            },
            content = {
                Column(
                    modifier = Modifier,
                    content = {
                        DatePicker(state = datePickerState)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            content = {
                                TextButton(
                                    onClick = {
                                        val todayFormatted = today.format(internalFormatter)
                                        onValueChange(todayFormatted)
                                        showDialog = false
                                    },
                                    content = {
                                        UiText(stringResource(R.string.str_today))
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
        verticalAlignment = Alignment.CenterVertically, content =  {
            OutlinedTextField(
                value = displayText, onValueChange = {},
                modifier = Modifier.weight(1f),
                enabled = enabled, readOnly = true,
                label = label?.let { { UiText(it) } }, placeholder = placeholder?.let { { UiText(it) } },
                leadingIcon = leadingIcon,
                trailingIcon = {
                    if (value.isNotBlank()) IconButton(
                        onClick = { onValueChange(Constants.STR_EMPTY) },
                        enabled = enabled,
                        content = { Icon(imageVector = Icons.Default.Clear, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                    )
                },
                supportingText = { if (isError && errorMessage.isNotEmpty())
                    ErrorSupportingText(errorMessage.map { "• ${it.asString(LocalContext.current)}" } ) },
                isError = isError, singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            IconButton(
                onClick = { showDialog = true },
                enabled = enabled,
                shape = MaterialTheme.shapes.extraSmall,
                colors = IconButtonDefaults.iconButtonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                content = { Icon(Icons.Default.Edit, contentDescription = null) }
            )
        }
    )
}