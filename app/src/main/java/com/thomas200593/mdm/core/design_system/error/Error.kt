package com.thomas200593.mdm.core.design_system.error

import androidx.annotation.StringRes
import androidx.sqlite.SQLiteException
import com.thomas200593.mdm.core.design_system.util.Constants

sealed class Error(
    open val code: String,
    open val emoji: String,
    @StringRes open val localizedMessage: Int? = null,
    override val message: String? = Constants.STR_EMPTY,
    override val cause: Throwable? = null
) : Throwable(message = message, cause = cause) {
    // **Generic Unknown Error
    data class UnknownError(
        override val code: String = "ERR_UNKNOWN",
        override val emoji: String = "❓❌️",
        override val localizedMessage: Int? = null,
        override val message: String? = "Unknown error occurred",
        override val cause: Throwable? = null
    ) : Input(code, emoji, localizedMessage, message, cause)
    // **DB Operation Errors** (DAO or SQL errors)
    sealed class Database(
        override val code: String,
        override val emoji: String,
        override val localizedMessage: Int?,
        override val message: String? = "Database error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, localizedMessage, message, cause) {
        data class DaoInsertError(
            override val code: String = "ERROR_DB_DAO_INSERT",
            override val emoji: String = "💾❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Database insert operation failed",
            override val cause: Throwable? = SQLiteException()
        ) : Database(code, emoji, localizedMessage, message, cause)
        data class DaoQueryError(
            override val code: String = "ERROR_DB_DAO_QUERY",
            override val emoji: String = "💾❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Database query operation failed",
            override val cause: Throwable? = SQLiteException()
        ) : Database(code, emoji, localizedMessage, message, cause)
        data class DaoQueryNoDataError(
            override val code: String = "ERROR_DB_DAO_QUERY_NO_DATA",
            override val emoji: String = "💾❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Database query returned no data",
            override val cause: Throwable? = NoSuchElementException(),
        ) : Database(code, emoji, localizedMessage, message, cause)
    }
    // **Input Errors**
    sealed class Input(
        override val code: String,
        override val emoji: String,
        override val localizedMessage: Int?,
        override val message: String? = "Input error",
        override val cause: Throwable? = null
    ) : Error (code, emoji, localizedMessage, message, cause) {
        data class MalformedError(
            override val code: String = "ERR_INPUT_MALFORMED",
            override val emoji: String = "📝❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Input data is malformed",
            override val cause: Throwable? = IllegalArgumentException()
        ) : Input(code, emoji, localizedMessage, message, cause)
    }
    // **Data Errors**
    sealed class Data(
        override val code: String,
        override val emoji: String,
        override val localizedMessage: Int?,
        override val message: String? = "Data error",
        override val cause: Throwable? = null
    ) : Error (code, emoji, localizedMessage, message, cause) {
        data class DuplicateError(
            override val code: String = "ERR_DATA_DUPLICATE",
            override val emoji: String = "🗃️❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Duplicate data found",
            override val cause: Throwable? = null
        ) : Data(code, emoji, localizedMessage, message, cause)
        data class NotFoundError(
            override val code: String = "ERR_DATA_NOT_FOUND",
            override val emoji: String = "🗃️❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Data not found",
            override val cause: Throwable? = null
        ) : Data(code, emoji, localizedMessage, message, cause)
        data class ValidationError(
            override val code: String = "ERR_DATA_VALIDATION",
            override val emoji: String = "🗃️❌️",
            override val localizedMessage: Int? = null,
            override val message: String? = "Validation data error",
            override val cause: Throwable? = IllegalStateException()
        ) : Data(code, emoji, localizedMessage, message, cause)
    }
}