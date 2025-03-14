package com.thomas200593.mdm.core.ui.component.text_field._domain

interface BaseValidation<String, ValidationResult> {
    fun validate(
        input: String,
        minLength: Int? = null,
        maxLength: Int? = null,
        required: Boolean? = false,
        regex: Regex? = null
    ): ValidationResult
}