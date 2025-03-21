package com.thomas200593.mdm.core.design_system.error

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface DatabaseError : BaseError {
    val args: List<String>

    override val message get() = UiText.DynamicString(errorMessage.format(*args.toTypedArray()))
    val errorMessage: String
    override val code: String
    override val emoji: String? get() = null // Default hidden for security

    // 🔌 Connection Issues
    sealed interface ConnectionError : DatabaseError

    data class ConnectionFailed(private val reason: String? = null) : ConnectionError {
        override val args = listOf(reason ?: "Unknown issue")
        override val code get() = "DB-7000"
        override val errorMessage get() = "We’re having trouble connecting. Please check your internet."
        override val emoji get() = "🔌"
        override val logMessage get() = "Database connection failed: $reason"
    }

    // ⚠️ Query Execution Issues
    sealed interface QueryError : DatabaseError

    data class QueryFailed(private val details: String? = null) : QueryError {
        override val args = listOf(details ?: "N/A")
        override val code get() = "DB-7001"
        override val errorMessage get() = "Something went wrong. Please try again later."
        override val emoji get() = "⚠️"
        override val logMessage get() = "Query execution failed: $details"
    }

    // 🔍 Record Not Found
    sealed interface DataError : DatabaseError

    data class RecordNotFound(private val table: String? = null) : DataError {
        override val args = listOf(table ?: "Unknown")
        override val code get() = "DB-7002"
        override val errorMessage get() = "Oops! We couldn’t find that information."
        override val emoji get() = "🔍"
        override val logMessage get() = "Record not found in $table"
    }

    // ⚠️ Duplicate Entry
    data class DuplicateEntry(private val field: String) : DataError {
        override val args = listOf(field)
        override val code get() = "DB-7003"
        override val errorMessage get() = "This already exists. Please check and try again."
        override val emoji get() = "⚠️"
        override val logMessage get() = "Duplicate entry detected for field: $field"
    }

    // 🚧 Constraint Violations
    data class ConstraintViolation(private val constraint: String? = null) : DataError {
        override val args = listOf(constraint ?: "N/A")
        override val code get() = "DB-7004"
        override val errorMessage get() = "That action isn't allowed. Please check your input."
        override val emoji get() = "🚧"
        override val logMessage get() = "Constraint violation: $constraint"
    }

    // 🔄 Deadlock Detected
    data class DeadlockDetected(private val process: String? = null) : DatabaseError {
        override val args = listOf(process ?: "Unknown process")
        override val code get() = "DB-7005"
        override val errorMessage get() = "The system is busy right now. Please try again in a moment."
        override val emoji get() = "🔄"
        override val logMessage get() = "Deadlock detected in process: $process"
    }

    // 💳 Transaction Issues
    sealed interface TransactionError : DatabaseError

    data class TransactionFailed(private val transactionId: String? = null) : TransactionError {
        override val args = listOf(transactionId ?: "Unknown")
        override val code get() = "DB-7006"
        override val errorMessage get() = "We couldn’t complete your transaction. Please try again."
        override val emoji get() = "💳"
        override val logMessage get() = "Transaction $transactionId failed"
    }

    // 🚫 Unauthorized Access
    sealed interface SecurityError : DatabaseError

    data class UnauthorizedAccess(private val user: String? = null) : SecurityError {
        override val args = listOf(user ?: "Unknown user")
        override val code get() = "DB-7007"
        override val errorMessage get() = "Access denied. You don’t have permission for this action."
        override val emoji get() = "🚫"
        override val logMessage get() = "Unauthorized access attempt by user: $user"
    }

    // ⏳ Timeout Issues
    data class Timeout(private val duration: String? = null) : DatabaseError {
        override val args = listOf(duration ?: "N/A")
        override val code get() = "DB-7008"
        override val errorMessage get() = "This is taking longer than expected. Please try again later."
        override val emoji get() = "⏳"
        override val logMessage get() = "Database request timed out after $duration"
    }

    // 📜 SQL Syntax Errors
    data class SyntaxError(private val error: String? = null) : QueryError {
        override val args = listOf(error ?: "Syntax issue")
        override val code get() = "DB-7009"
        override val errorMessage get() = "Something went wrong with the database query."
        override val emoji get() = "📜"
        override val logMessage get() = "SQL syntax error: $error"
    }
}