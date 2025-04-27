package com.thomas200593.mdm.core.ui.component.text_field.domain

import android.util.Patterns
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText
import com.thomas200593.mdm.core.ui.component.text_field.state.ValidationResult

class TxtFieldEmailValidation : BaseValidation<String, ValidationResult> {
    override fun validate(
        input: String,
        minLength: Int?,
        maxLength: Int?,
        required: Boolean?,
        regex: Regex?
    ) : ValidationResult {
        // Error Messages container
        val errors = mutableListOf<UiText>()
        val maxLen = maxLength ?: 200
        // Email Format Validation
        val emailPattern = regex ?: Patterns.EMAIL_ADDRESS.toRegex()
        // Required Check
        if (required == true && input.isBlank()) errors.add(UiText.DynamicString("This field is required!"))
        // Min Length Checks
        if (minLength != null && input.length < minLength) errors.add(UiText.DynamicString("This field required minimum $minLength character(s)."))
        // Max Length Checks
        if (input.length > (maxLength ?: maxLen)) errors.add(UiText.DynamicString("This field requires a maximum of ${maxLength ?: maxLen} character(s)."))
        //Pattern Matching
        if (!emailPattern.matches(input)) errors.add(UiText.DynamicString("Invalid e-mail format!."))
        return ValidationResult(errorMessages = errors)
    }
}