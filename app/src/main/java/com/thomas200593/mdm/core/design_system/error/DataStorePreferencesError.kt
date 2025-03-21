package com.thomas200593.mdm.core.design_system.error

// ⚙️ DataStore / Preferences Errors
sealed interface DataStoreError : BaseError {
    data object ReadFailed : DataStoreError {
        override val code get() = "DS-001"
        override val emoji get() = "⚠️"
        override val message get() = "Failed to read preferences."
    }

    data object WriteFailed : DataStoreError {
        override val code get() = "DS-002"
        override val emoji get() = "📄"
        override val message get() = "Failed to save preferences."
    }
}