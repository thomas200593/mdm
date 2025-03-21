package com.thomas200593.mdm.core.design_system.error

// 🔧 System Errors
sealed interface SystemError : BaseError {
    data object UnexpectedCrash : SystemError {
        override val code get() = "SYS-001"
        override val emoji get() = "💥"
        override val message get() = "Unexpected crash."
    }

    data object MemoryOverflow : SystemError {
        override val code get() = "SYS-002"
        override val emoji get() = "🧠"
        override val message get() = "Memory overflow."
    }
}