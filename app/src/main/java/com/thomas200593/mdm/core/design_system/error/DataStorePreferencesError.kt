package com.thomas200593.mdm.core.design_system.error

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface DataStorePreferencesError : BaseError {
    val args: List<String>
    override val message get() = UiText.DynamicString(errorMessage.format(*args.toTypedArray()))
    val errorMessage: String
    override val emoji: String? get() = "⚙️" // Default icon for settings-related issues

    // 🔄 **Read Error**
    data class ReadError(private val key: String) : DataStorePreferencesError {
        override val args = listOf(key)
        override val code get() = "DSP-8000"
        override val errorMessage get() = "We couldn’t load your settings. Please try again."
        override val logMessage get() = "Failed to read DataStore key: $key"
    }

    // ✍️ **Write Error**
    data class WriteError(private val key: String) : DataStorePreferencesError {
        override val args = listOf(key)
        override val code get() = "DSP-8001"
        override val errorMessage get() = "We couldn’t save your settings. Please try again."
        override val logMessage get() = "Failed to write DataStore key: $key"
    }

    // ❌ **Corruption Error**
    data class CorruptionError(private val filePath: String? = null) : DataStorePreferencesError {
        override val args = listOf(filePath ?: "Unknown location")
        override val code get() = "DSP-8002"
        override val errorMessage get() = "Your settings data is corrupted. Reset may be required."
        override val emoji get() = "❌"
        override val logMessage get() = "DataStore corruption detected at: $filePath"
    }
}