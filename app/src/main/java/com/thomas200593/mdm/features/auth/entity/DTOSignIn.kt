package com.thomas200593.mdm.features.auth.entity

data class DTOSignIn (
    val email: String,
    val authType: AuthType,
    val timestamp: Long
)