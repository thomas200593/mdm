package com.thomas200593.mdm.features.introduction.initialization.ui.state

import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitialization

sealed interface ResultInitializationState {
    data object Idle : ResultInitializationState
    data object Loading : ResultInitializationState
    data class Success(val result: DTOInitialization) : ResultInitializationState
    data class Failure(val t: Error) : ResultInitializationState
}