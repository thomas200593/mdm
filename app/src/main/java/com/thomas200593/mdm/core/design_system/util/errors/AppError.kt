package com.thomas200593.mdm.core.design_system.util.errors

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface AppError {
    val code: String // Unique error code
    val message: UiText // Multiple error messages if needed
    val emoji: String // Stylish emoji for UX
}