package com.thomas200593.mdm.core.design_system.error

import com.thomas200593.mdm.core.design_system.util.Constants

sealed class Error(
    open val code: String,
    open val emoji: String,
    override val message: String? = Constants.STR_EMPTY,
    override val cause: Throwable? = null
) : Throwable(message = message, cause = cause) {

    // **App Level Errors** (General errors such as IO or Networking)
    sealed class AppLevel(
        override val code: String,
        override val emoji: String,
        override val message: String? = "General App-level error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class IOError(
            override val code: String = "APP_ERR_IO",
            override val emoji: String = "💻",
            override val message: String = "Input/Output operation failed",
            override val cause: Throwable? = null
        ) : AppLevel(code, emoji, message, cause)

        data class NetworkError(
            override val code: String = "APP_ERR_NETWORK",
            override val emoji: String = "🌐",
            override val message: String = "Network connection error",
            override val cause: Throwable? = null
        ) : AppLevel(code, emoji, message, cause)
    }

    // **DB Operation Errors** (DAO or SQL errors)
    sealed class Database(
        override val code: String,
        override val emoji: String,
        override val message: String? = "Database error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class FailedOperation(
            override val code: String = "DB_ERR_FAILED",
            override val emoji: String = "💾",
            override val message: String = "Database operation failed",
            override val cause: Throwable? = null
        ) : Database(code, emoji, message, cause)

        data class NoDataFound(
            override val code: String = "DB_ERR_NO_DATA",
            override val emoji: String = "📭",
            override val message: String = "No data found in the database",
            override val cause: Throwable? = null
        ) : Database(code, emoji, message, cause)
    }

    // **Data Result Errors** (Errors specific to Data-related operations, e.g., not found, insert/update errors)
    sealed class DataResult(
        override val code: String,
        override val emoji: String,
        override val message: String? = "Data-related error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class NotFound(
            override val code: String = "DATA_ERR_NOT_FOUND",
            override val emoji: String = "❌",
            override val message: String = "Requested data not found",
            override val cause: Throwable? = null
        ) : DataResult(code, emoji, message, cause)

        data class InsertError(
            override val code: String = "DATA_ERR_INSERT",
            override val emoji: String = "✍️",
            override val message: String = "Error inserting data",
            override val cause: Throwable? = null
        ) : DataResult(code, emoji, message, cause)

        data class UpdateError(
            override val code: String = "DATA_ERR_UPDATE",
            override val emoji: String = "🔄",
            override val message: String = "Error updating data",
            override val cause: Throwable? = null
        ) : DataResult(code, emoji, message, cause)
    }

    // **Module Specific Errors** (Errors specific to certain application modules)

    // **Initialization Errors**
    sealed class Initialization(
        override val code: String,
        override val emoji: String,
        override val message: String? = "Initialization error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class InvalidConfig(
            override val code: String = "INIT_ERR_INVALID_CONFIG",
            override val emoji: String = "⚙️",
            override val message: String = "Invalid configuration during initialization",
            override val cause: Throwable? = null
        ) : Initialization(code, emoji, message, cause)
    }

    // **Authentication Errors**
    sealed class Auth(
        override val code: String,
        override val emoji: String,
        override val message: String? = "Authentication error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class InvalidCredentials(
            override val code: String = "AUTH_ERR_INVALID_CREDENTIALS",
            override val emoji: String = "🔑",
            override val message: String = "Invalid credentials",
            override val cause: Throwable? = null
        ) : Auth(code, emoji, message, cause)

        data class UserNotFound(
            override val code: String = "AUTH_ERR_USER_NOT_FOUND",
            override val emoji: String = "🙍‍♂️",
            override val message: String = "User not found during authentication",
            override val cause: Throwable? = null
        ) : Auth(code, emoji, message, cause)
    }

    // **Session Errors**
    sealed class Session(
        override val code: String,
        override val emoji: String,
        override val message: String? = "Session error",
        override val cause: Throwable? = null
    ) : Error(code, emoji, message, cause) {

        data class Expired(
            override val code: String = "SESSION_ERR_EXPIRED",
            override val emoji: String = "⌛",
            override val message: String = "Session has expired",
            override val cause: Throwable? = null
        ) : Session(code, emoji, message, cause)

        data class NotFound(
            override val code: String = "SESSION_ERR_NOT_FOUND",
            override val emoji: String = "🔍",
            override val message: String = "Session not found",
            override val cause: Throwable? = null
        ) : Session(code, emoji, message, cause)
    }
}