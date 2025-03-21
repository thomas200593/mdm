package com.thomas200593.mdm.core.design_system.error

// 🔄 I/O Errors (New Category)
sealed interface IOError : BaseError {
    data object ReadFailed : IOError {
        override val code get() = "IO-001"
        override val emoji get() = "📜"
        override val message get() = "Failed to read file."
    }

    data object WriteFailed : IOError {
        override val code get() = "IO-002"
        override val emoji get() = "📝"
        override val message get() = "Failed to write file."
    }

    data object PermissionDenied : IOError {
        override val code get() = "IO-003"
        override val emoji get() = "🔐"
        override val message get() = "Permission denied for I/O operation."
    }

    data object DeviceDisconnected : IOError {
        override val code get() = "IO-004"
        override val emoji get() = "🔌"
        override val message get() = "External device disconnected."
    }
}