package com.thomas200593.mdm.core.design_system.error

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText

sealed interface RemoteSourceError : BaseError {
    val args: List<String>
    override val message get() = UiText.DynamicString(errorMessage.format(*args.toTypedArray()))
    val errorMessage: String
    override val emoji: String? get() = "🌐" // Default to globe for remote issues

    // 🌍 **Network Issues**
    sealed interface NetworkError : RemoteSourceError

    data class NoInternet(private val reason: String? = null) : NetworkError {
        override val args = listOf(reason ?: "No internet")
        override val code get() = "RS-9000"
        override val errorMessage get() = "No internet connection. Please check your network."
        override val emoji get() = "📡"
        override val logMessage get() = "No internet detected: $reason"
    }

    data class Timeout(private val duration: String? = null) : NetworkError {
        override val args = listOf(duration ?: "N/A")
        override val code get() = "RS-9001"
        override val errorMessage get() = "The request took too long. Please try again."
        override val emoji get() = "⏳"
        override val logMessage get() = "Network timeout after $duration"
    }

    // 🔑 **Authentication Issues**
    sealed interface AuthError : RemoteSourceError

    data class Unauthorized(private val user: String? = null) : AuthError {
        override val args = listOf(user ?: "Unknown user")
        override val code get() = "RS-9002"
        override val errorMessage get() = "You’re not authorized for this action."
        override val emoji get() = "🔑"
        override val logMessage get() = "Unauthorized request by user: $user"
    }

    data object TokenExpired : AuthError {
        override val args = emptyList<String>()
        override val code get() = "RS-9003"
        override val errorMessage get() = "Your session has expired. Please sign in again."
        override val emoji get() = "🔄"
        override val logMessage get() = "Authentication token expired"
    }
}
