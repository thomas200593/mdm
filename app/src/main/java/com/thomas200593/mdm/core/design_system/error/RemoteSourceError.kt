package com.thomas200593.mdm.core.design_system.error

// 🌍 API / Remote Errors
sealed interface ApiError : BaseError {
    sealed interface Network : ApiError
    sealed interface Authentication : ApiError
    sealed interface Server : ApiError
    sealed interface Client : ApiError

    data object RequestTimeout : Network {
        override val code get() = "API-001"
        override val emoji get() = "⏳"
        override val message get() = "The request timed out."
    }

    data object TokenExpired : Authentication {
        override val code get() = "API-002"
        override val emoji get() = "🔑"
        override val message get() = "Session expired. Please log in."
    }

    data object InternalServerError : Server {
        override val code get() = "API-003"
        override val emoji get() = "🔥"
        override val message get() = "Server error. Try again later."
    }

    data object BadRequest : Client {
        override val code get() = "API-004"
        override val emoji get() = "🚫"
        override val message get() = "Invalid request."
    }
}