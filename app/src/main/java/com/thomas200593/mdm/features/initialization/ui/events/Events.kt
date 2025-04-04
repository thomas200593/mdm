package com.thomas200593.mdm.features.initialization.ui.events

/**
 * Represents all possible events that can occur in the application.
 * This is a sealed interface, meaning all implementations must be defined within this hierarchy.
 */
sealed interface Events {
    /**
     * Defines events related to screen changes.
     */
    sealed interface Screen : Events {
        /**
         * Event triggered when the screen is opened.
         */
        data object OnOpen : Screen
    }
    /**
     * Defines events related to the TopAppBar component.
     */
    sealed interface TopAppBar : Events {
        /**
         * Defines events for a button inside the TopAppBar.
         */
        sealed interface BtnScrDesc : TopAppBar {
            /**
             * Event triggered when the button is clicked.
             */
            data object OnClick : BtnScrDesc
            /**
             * Event triggered when the button is dismissed or canceled.
             */
            data object OnDismiss : BtnScrDesc
        }
    }
    /**
     * Defines events occurring within the main content area.
     */
    sealed interface Content : Events {
        /**
         * Defines events related to form field value changes.
         */
        sealed interface Form : Content {
            /**
             * Event triggered when the first name input value changes.
             *
             * @property firstName The updated first name value.
             */
            data class FldValChgFirstName(val firstName: CharSequence) : Form
            /**
             * Event triggered when the last name input value changes.
             *
             * @property lastName The updated last name value.
             */
            data class FldValChgLastName(val lastName: CharSequence) : Form
            /**
             * Event triggered when the email input value changes.
             *
             * @property email The updated email value.
             */
            data class FldValChgEmail(val email: CharSequence) : Form
            /**
             * Event triggered when the password input value changes.
             *
             * @property password The updated password value.
             */
            data class FldValChgPassword(val password: CharSequence) : Form
        }
    }
    /**
     * Defines events related to the BottomAppBar component.
     */
    sealed interface BottomAppBar : Events {
        /**
         * Defines events for the "Proceed" button inside the BottomAppBar.
         */
        sealed interface BtnProceedInit : BottomAppBar {
            /**
             * Event triggered when the button is clicked.
             */
            data object OnClick : BtnProceedInit
        }
    }
    /**
     * Defines events related to dialogs in the application.
     */
    sealed interface Dialog : Events {
        /**
         * Event triggered when the success dialog is dismissed.
         */
        data object InitializationSuccessOnDismiss : Dialog
        /**
         * Event triggered when the error dialog is dismissed.
         */
        data object InitializationErrorOnDismiss : Dialog
    }
}