package com.thomas200593.mdm.core.design_system.util.errors

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface StorageError : AppError

// 🔹 Device Storage Errors (0x7200 - 0x72FF)
data class InsufficientStorage(
    override val code: String = "0x7201",
    override val message: UiText = UiText.DynamicString("Not enough storage space available."),
    override val emoji: String = "📦❌"
) : StorageError

data class IOError(
    override val code: String = "0x7202",
    override val message: UiText = UiText.DynamicString("Unexpected storage I/O error."),
    override val emoji: String = "⚙️💥"
) : StorageError

// 🔹 Database Errors (0x7300 - 0x73FF)
data class QueryFailure(
    override val code: String = "0x7301",
    override val message: UiText = UiText.DynamicString("Failed to fetch data from database."),
    override val emoji: String = "🔍❌"
) : StorageError

data class WriteConflict(
    override val code: String = "0x7302",
    override val message: UiText = UiText.DynamicString("Database write conflict. Try again."),
    override val emoji: String = "✍️⚠️"
) : StorageError

data class MigrationFailure(
    override val code: String = "0x7303",
    override val message: UiText = UiText.DynamicString("Database migration failed. App may need a reinstall."),
    override val emoji: String = "🔄🛑"
) : StorageError

// 🔹 File System Errors (0x7400 - 0x74FF)
data class FileReadError(
    override val code: String = "0x7401",
    override val message: UiText = UiText.DynamicString("Failed to read file. Check file path."),
    override val emoji: String = "📄⚠️"
) : StorageError

data class FileWriteError(
    override val code: String = "0x7402",
    override val message: UiText = UiText.DynamicString("Failed to write file. Storage may be full."),
    override val emoji: String = "✍️❌"
) : StorageError

data class PermissionDenied(
    override val code: String = "0x7403",
    override val message: UiText = UiText.DynamicString("Storage permission denied. Enable in settings."),
    override val emoji: String = "🔒🚫"
) : StorageError