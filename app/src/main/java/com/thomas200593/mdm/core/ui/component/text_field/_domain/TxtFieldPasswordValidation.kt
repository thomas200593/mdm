package com.thomas200593.mdm.core.ui.component.text_field._domain

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.core.ui.component.text_field._state.ValidationResult

class TxtFieldPasswordValidation : BaseValidation<String, ValidationResult> {
    override fun validate(
        input: String,
        minLength: Int?,
        maxLength: Int?,
        required: Boolean?,
        regex: Regex?
    ): ValidationResult {
        // Error Messages container
        val errors = mutableListOf<UiText>()
        // Define min/max length with defaults
        val minLen = minLength ?: 6
        val maxLen = maxLength ?: 200
        // Password Format Validation
        val passwordPattern = regex ?: Regex(buildString {
            append("^(?=.*[0-9])")        // At least one digit
            append("(?=.*[a-z])")         // At least one lowercase letter
            append("(?=.*[A-Z])")         // At least one uppercase letter
            append("(?=.*[\\W_])")        // At least one special character (including `_`)
            append(".{")
            append(minLen)
            append(",")
            append(maxLen)
            append("}\$")                 // Length enforcement
        })
        // Required Check
        if (required == true && input.isBlank()) {
            errors.add(UiText.DynamicString("This field is required!"))
        }
        // Min Length Check
        if (input.length < (minLength ?: minLen)) {
            errors.add(UiText.DynamicString("This field requires a minimum of ${minLength ?: minLen} character(s)."))
        }
        // Max Length Check
        if (input.length > (maxLength ?: maxLen)) {
            errors.add(UiText.DynamicString("This field requires a maximum of ${maxLength ?: maxLen} character(s)."))
        }
        // Pattern Matching
        if (!passwordPattern.matches(input)) {
            errors.add(UiText.DynamicString("Password must contain at least 1 uppercase letter, 1 digit, and 1 special symbol."))
        }
        return ValidationResult(errorMessages = errors)
    }
}