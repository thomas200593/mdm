package com.thomas200593.mdm.core.ui.component.text_field._domain

import android.util.Patterns
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.core.ui.component.text_field._state.ValidationResult

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
        // Email Format Validation
        val emailPattern = regex ?: Patterns.EMAIL_ADDRESS.toRegex()
        // Required Check
        if (required == true && input.isBlank()) {
            errors.add(UiText.DynamicString("This field is required!"))
        }
        // Min Length Checks
        if (minLength != null && input.length < minLength) {
            errors.add(UiText.DynamicString("This field required minimum $minLength character(s)."))
        }
        // Max Length Checks
        if (maxLength != null && input.length > maxLength) {
            errors.add(UiText.DynamicString("This field required maximum $maxLength character(s)."))
        }
        //Pattern Matching
        if (!emailPattern.matches(input)) {
            errors.add(UiText.DynamicString("Invalid e-mail format!."))
        }
        return ValidationResult(isSuccess = errors.isEmpty(), errorMessages = if (errors.isEmpty()) null else errors)
    }
}