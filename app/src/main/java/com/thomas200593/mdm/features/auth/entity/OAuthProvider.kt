package com.thomas200593.mdm.features.auth.entity

enum class OAuthProvider(
    val code: String,
    val title: String
) {
    GOOGLE(
        code = "oauth_google",
        title = "auth_provider_google"
    ),
    FACEBOOK(
        code = "oauth_facebook",
        title = "auth_provider_facebook"
    )
}