package com.thomas200593.mdm.features.user_management.security.auth.entity

data class DTOSignIn (
    val email: String,
    val authType: AuthType,
    val timestamp: Long
)