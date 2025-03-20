package com.thomas200593.mdm.features.initialization.entity

import com.thomas200593.mdm.features.auth.entity.AuthType
import kotlinx.serialization.Serializable

@Serializable
data class DTOInitialization(
    val firstName: String,
    val lastName: String,
    val email: String,
    val authType: AuthType
)
