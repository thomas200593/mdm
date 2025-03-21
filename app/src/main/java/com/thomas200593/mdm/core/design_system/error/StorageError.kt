package com.thomas200593.mdm.core.design_system.error

// 💾 Storage Errors
sealed interface StorageError : BaseError {
    data object FileNotFound : StorageError {
        override val code get() = "ST-001"
        override val emoji get() = "📂"
        override val message get() = "File not found."
    }

    data object DiskFull : StorageError {
        override val code get() = "ST-002"
        override val emoji get() = "💾"
        override val message get() = "Storage full."
    }
}