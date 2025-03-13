package com.thomas200593.mdm.core.ui.component.text_field._state

data class ValidationResult (
    val isSuccess: Boolean,
    val errorMessages: List<UiText>? = null
)