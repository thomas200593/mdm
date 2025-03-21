package com.thomas200593.mdm.core.design_system.error

// 🗄 Database Errors
sealed interface DatabaseError : BaseError {
    sealed interface Connection : DatabaseError
    sealed interface Query : DatabaseError
    sealed interface Data : DatabaseError

    data object ConnectionFailed : Connection {
        override val code get() = "DB-001"
        override val emoji get() = "🔌"
        override val message get() = "Database connection failed."
    }

    data object QueryFailed : Query {
        override val code get() = "DB-002"
        override val emoji get() = "⚠️"
        override val message get() = "Query execution failed."
    }

    data object RecordNotFound : Data {
        override val code get() = "DB-003"
        override val emoji get() = "🔍"
        override val message get() = "Record not found."
    }
}