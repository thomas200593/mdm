package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.initialization.entity.DTOInitialization

/**
 * Represents the state of an initialization process.
 * This sealed interface ensures that only predefined states are used.
 */
sealed interface ResultInitializationState {
    /**
     * Represents the idle state when no initialization process is active.
     */
    data object Idle : ResultInitializationState
    /**
     * Represents the loading state when initialization is in progress.
     */
    data object Loading : ResultInitializationState
    /**
     * Represents a successful initialization result.
     *
     * @property result The data transfer object (DTO) containing initialization details.
     */
    data class Success(val result: DTOInitialization) : ResultInitializationState
    /**
     * Represents an error state that occurred during initialization.
     *
     * @property t The throwable error that caused the initialization to fail.
     */
    data class Error(val t: Throwable) : ResultInitializationState
}