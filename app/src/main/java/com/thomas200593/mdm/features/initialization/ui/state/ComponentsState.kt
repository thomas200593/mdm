package com.thomas200593.mdm.features.initialization.ui.state

import com.thomas200593.mdm.features.conf.common.entity.Common

/**
 * Represents the state of various UI components in the application.
 * This is a sealed interface, ensuring that only defined states can be used.
 */
sealed interface ComponentsState {
    /**
     * Represents a loading state when components are being initialized.
     */
    data object Loading : ComponentsState
    /**
     * Represents a state where components have been successfully loaded.
     *
     * @property confCommon Configuration data shared across components.
     * @property formState The current state of the form.
     * @property dialogState The current state of dialogs in the application.
     * @property resultInitializationState The result of the initialization process.
     */
    data class Loaded(
        val confCommon: Common,
        val formState: FormState,
        val dialogState: DialogState,
        val resultInitializationState: ResultInitializationState
    ) : ComponentsState
}