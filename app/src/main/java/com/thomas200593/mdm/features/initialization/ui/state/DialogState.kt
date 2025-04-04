package com.thomas200593.mdm.features.initialization.ui.state

/**
 * Represents the state of dialogs in the application.
 * This sealed interface ensures that only predefined dialog states are used.
 */
sealed interface DialogState {
    /**
     * Represents the absence of any active dialog.
     */
    data object None : DialogState
    /**
     * Represents a dialog displaying information about the screen description.
     */
    data object InfoScrDesc : DialogState
    /**
     * Represents an error dialog displaying an exception message.
     *
     * @property t The throwable error that caused the dialog to be shown.
     */
    data class Error(val t: Throwable) : DialogState
    /**
     * Represents a success dialog shown after a successful initialization.
     */
    data object SuccessInitialization : DialogState
}