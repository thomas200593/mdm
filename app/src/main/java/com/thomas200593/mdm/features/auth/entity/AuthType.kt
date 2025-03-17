package com.thomas200593.mdm.features.auth.entity

sealed interface AuthType {
    data class LocalEmailPassword(val email: String, val password: String) : AuthType
    data class OAuth(val provider: OAuthProvider, val token: String) : AuthType
}