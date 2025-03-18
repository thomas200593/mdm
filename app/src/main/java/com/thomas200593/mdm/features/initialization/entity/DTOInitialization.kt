package com.thomas200593.mdm.features.initialization.entity

import kotlinx.serialization.Serializable

@Serializable
data class DTOInitialization (
    val email: String,
    val password: String
)