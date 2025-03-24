package com.thomas200593.mdm.features.auth.entity

enum class AuthProvider(
    val code: String,
    val title: String
) {
    LOCAL_EMAIL_PASSWORD(
        code = "auth_local_email_password",
        title = "Local (Email & Password)"
    )
}