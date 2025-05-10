package com.thomas200593.mdm.features.introduction.initialization.ui_2.state

import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitialization

sealed interface ResultInitialization {
    data object Idle : ResultInitialization
    data object Loading : ResultInitialization
    data class Success(val result: DTOInitialization) : ResultInitialization
    data class Error(val t: Throwable) : ResultInitialization
}