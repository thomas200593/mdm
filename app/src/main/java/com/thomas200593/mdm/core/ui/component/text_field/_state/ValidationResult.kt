package com.thomas200593.mdm.core.ui.component.text_field._state

data class ValidationResult (
    val errorMessages: List<UiText> = emptyList<UiText>(),
    val isSuccess: Boolean = errorMessages.isEmpty()
)