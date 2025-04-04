package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.initialization.entity.DTOInitialization

sealed interface ResultInitializationState {
    data object Idle : ResultInitializationState
    data object Loading : ResultInitializationState
    data class Success(val result: DTOInitialization) : ResultInitializationState
    data class Error(val t : Throwable) : ResultInitializationState
}