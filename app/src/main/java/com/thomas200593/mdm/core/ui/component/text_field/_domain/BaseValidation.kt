package com.thomas200593.mdm.core.ui.component.text_field._domain

interface BaseValidation<I, R> {
    fun validate(
        input: I,
        minLength: Int? = null,
        maxLength: Int? = null,
        required: Boolean? = false,
        regex: Regex? = null
    ) : R
}