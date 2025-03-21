package com.thomas200593.mdm.core.design_system.error

// 🔒 Security Errors
sealed interface SecurityError : BaseError {
    data object UnauthorizedAccess : SecurityError {
        override val code get() = "SEC-001"
        override val emoji get() = "🚫"
        override val message get() = "Unauthorized access."
    }

    data object DataTampered : SecurityError {
        override val code get() = "SEC-002"
        override val emoji get() = "🔓"
        override val message get() = "Data integrity compromised."
    }
}

