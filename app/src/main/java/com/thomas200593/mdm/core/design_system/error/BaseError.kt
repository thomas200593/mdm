package com.thomas200593.mdm.core.design_system.error

sealed interface BaseError {
    val code: String
    val emoji: String
    val message: String
    val cause: Throwable?
    /** Account Errors */
    sealed interface UserAccount : BaseError {
        data class NotFound (
            override val code: String = "ACCOUNT_ERR_NOT_FOUND",
            override val emoji: String = "🙍‍♂️",
            override val message: String = "Account Not Found",
            override val cause: Throwable? = null
        ) : UserAccount
    }
    /** Session Errors */
    sealed interface Session : BaseError {
        data class Create (
            override val code: String = "SESSION_ERR_CREATE",
            override val emoji: String = "",
            override val message: String = "Error during create session",
            override val cause: Throwable? = null
        ) : Session
        data class NotFound (
            override val code: String = "SESSION_ERR_NOT_FOUND",
            override val emoji: String = "",
            override val message: String = "Session expired",
            override val cause: Throwable? = null
        ) : Session
        data class Expired (
            override val code: String = "SESSION_ERR_EXPIRED",
            override val emoji: String = "",
            override val message: String = "Session expired",
            override val cause: Throwable? = null
        ) : Session
    }
    /** Database Error */
    sealed interface Database : BaseError {
        data class Failed(
            override val code: String = "DB_ERR_FAILED",
            override val emoji: String = "💾",
            override val message: String = "Database operation failed.",
            override val cause: Throwable? = null
        ) : Database
        data class NoData(
            override val code: String = "DB_ERR_NO_DATA",
            override val emoji: String = "📭",
            override val message: String = "No data found.",
            override val cause: Throwable? = null
        ) : Database
    }
    /** Generic Fallback */
    data class Unknown(
        override val code: String = "UNKNOWN_ERR",
        override val emoji: String = "💥",
        override val message: String = "Something went wrong.",
        override val cause: Throwable? = null
    ) : BaseError
}

fun Throwable.toBaseError(): BaseError = when (this) {
    /* Incorporate this */
    else -> BaseError.Unknown(message = this.message ?: BaseError.Unknown().message, cause = this)
}