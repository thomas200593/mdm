package com.thomas200593.mdm.core.design_system.error

// 📡 Network Errors
sealed interface NetworkError : BaseError {
    data object NoInternet : NetworkError {
        override val code get() = "NET-001"
        override val emoji get() = "📴"
        override val message get() = "No internet connection."
    }

    data object SlowConnection : NetworkError {
        override val code get() = "NET-002"
        override val emoji get() = "🐢"
        override val message get() = "Network is slow."
    }
}