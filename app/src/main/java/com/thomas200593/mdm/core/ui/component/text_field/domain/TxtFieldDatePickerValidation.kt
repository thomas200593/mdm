package com.thomas200593.mdm.core.ui.component.text_field.domain

import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.DateTimePattern
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import com.thomas200593.mdm.core.ui.component.text_field.state.ValidationResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class TxtFieldDatePickerValidation : BaseValidation<String, ValidationResult> {
    override fun validate(
        input : String,
        minLength : Int?,
        maxLength : Int?,
        required : Boolean?,
        regex : Regex?
    ): ValidationResult {
        val errors = mutableListOf<UiText>()
        val today = Constants.NOW_DATE
        val effectiveRegex = regex ?: Regex("""\d{4}-\d{2}-\d{2}""")
        val enforcedPattern = DateTimePattern.YYYY_MM_DD.pattern
        val enforcedFormat = DateTimeFormatter.ofPattern(enforcedPattern).withLocale(Locale.US)
        if (required == true && input.isBlank()) errors.add(UiText.DynamicString("This field is required!"))
        if (input.isNotBlank()) {
            if(!effectiveRegex.matches(input)) errors.add(UiText.DynamicString("Invalid date format. Expected [$enforcedPattern]"))
            val parsedDate = runCatching { LocalDate.parse(input, enforcedFormat) }.getOrNull() ?: return ValidationResult(errors + UiText.DynamicString("Unable to parse the date."))
            minLength ?.let {
                val minDate = today.plusYears(it.toLong())
                if (parsedDate.isBefore(minDate)) errors.add(UiText.DynamicString("Date must not be earlier than ${minDate.format(enforcedFormat)}."))
            }
            maxLength ?.let {
                val maxDate = today.plusYears(it.toLong())
                if (parsedDate.isAfter(maxDate)) errors.add(UiText.DynamicString("Date must not be later than ${maxDate.format(enforcedFormat)}."))
            }
        }
        return ValidationResult(errorMessages = errors)
    }
}