package com.thomas200593.mdm.core.design_system.error

import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface BaseError {
    val code: String
    val message: UiText
    val emoji: String? get() = null // Defaults to `null`
    val logMessage: String get() = message.toString() // Internal logging
    val stackTrace: String? get() = if(BuildConfig.DEBUG) Exception().stackTraceToString() else null
}