package com.thomas200593.mdm.features.auth.entity

data class AuthResult (
    val authType: AuthType,
    val email: String?,
    val displayName: String?,
    val sessionToken: String
)