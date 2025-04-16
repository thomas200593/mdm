package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.initialization.entity.DTOInitialization

sealed interface ResultInitialization {
    data object Idle : ResultInitialization
    data object Loading : ResultInitialization
    data class Success(val result: DTOInitialization) : ResultInitialization
    data class Error(val t: Throwable) : ResultInitialization
}