package com.thomas200593.mdm.core.design_system.error

// 📝 Validation Errors
sealed interface InputValidationError : BaseError {
    data object RequiredField : InputValidationError {
        override val code get() = "VAL-001"
        override val emoji get() = "⚠️"
        override val message get() = "This field is required."
    }

    data class MinLength(val min: Int) : InputValidationError {
        override val code get() = "VAL-002"
        override val emoji get() = "📏"
        override val message get() = "Must be at least $min characters long."
    }

    data class MaxLength(val max: Int) : InputValidationError {
        override val code get() = "VAL-003"
        override val emoji get() = "📏"
        override val message get() = "Must be at most $max characters long."
    }

    data object InvalidFormat : InputValidationError {
        override val code get() = "VAL-004"
        override val emoji get() = "🔤"
        override val message get() = "Invalid format."
    }

    data class RegexMismatch(val pattern: String) : InputValidationError {
        override val code get() = "VAL-005"
        override val emoji get() = "📝"
        override val message get() = "Input does not match the required pattern: $pattern"
    }
}