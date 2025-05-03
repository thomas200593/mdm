package com.thomas200593.mdm.features.user_management.security.auth.entity

import kotlinx.serialization.Serializable

@Serializable
enum class AuthProvider(
    val code: String,
    val title: String
) {
    @Serializable
    LOCAL_EMAIL_PASSWORD(
        code = "auth_local_email_password",
        title = "Local (Email & Password)"
    )
}