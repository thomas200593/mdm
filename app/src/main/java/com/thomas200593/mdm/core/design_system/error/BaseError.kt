package com.thomas200593.mdm.core.design_system.error

sealed interface BaseError {
    val code: String
    val emoji: String
    val message: String
}