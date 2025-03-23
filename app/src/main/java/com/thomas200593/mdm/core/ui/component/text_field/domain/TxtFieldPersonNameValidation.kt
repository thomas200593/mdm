package com.thomas200593.mdm.core.ui.component.text_field.domain

import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import com.thomas200593.mdm.core.ui.component.text_field.state.ValidationResult

class TxtFieldPersonNameValidation : BaseValidation<String, ValidationResult> {
    override fun validate(
        input: String,
        minLength: Int?,
        maxLength: Int?,
        required: Boolean?,
        regex: Regex?
    ): ValidationResult {
        // Error Messages container
        val errors = mutableListOf<UiText>()
        val maxLen = maxLength ?: 200
        // Name Format Validation (Allows letters, spaces, hyphens, and apostrophes; no numbers or newlines)
        val namePattern = regex ?: """^[\p{L}\p{M}\p{Pd}\p{Zs}.'’]{1,200}$""".toRegex()
        // Required Check
        if (required == true && input.isBlank()) {
            errors.add(UiText.DynamicString("This field is required!"))
        }
        // Apply validation if input is not empty
        if (input.isNotBlank()) {
            // Min Length Checks
            if (minLength != null && input.length < minLength) {
                errors.add(UiText.DynamicString("This field requires a minimum of $minLength character(s)."))
            }
            // Max Length Checks
            if (input.length > maxLen) {
                errors.add(UiText.DynamicString("This field allows up to $maxLen character(s)."))
            }
            // Pattern Matching
            if (!namePattern.matches(input)) {
                errors.add(UiText.DynamicString("Invalid name format! Only letters, spaces, hyphens, and apostrophes are allowed."))
            }
        }
        return ValidationResult(errorMessages = errors)
    }
}