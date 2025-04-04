package com.thomas200593.mdm.features.initialization.ui.state

import androidx.compose.foundation.text.input.TextFieldState
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

/**
 * Represents the state of a form, including input fields and validation states.
 *
 * This class manages the form fields, their error states, visibility, and validation logic.
 *
 * @property fldFirstName The state of the first name input field.
 * @property fldFirstNameEnabled Whether the first name field is enabled.
 * @property fldFirstNameError A list of validation errors for the first name field.
 * @property fldLastName The state of the last name input field.
 * @property fldLastNameEnabled Whether the last name field is enabled.
 * @property fldLastNameError A list of validation errors for the last name field.
 * @property fldEmail The state of the email input field.
 * @property fldEmailEnabled Whether the email field is enabled.
 * @property fldEmailError A list of validation errors for the email field.
 * @property fldPassword The state of the password input field.
 * @property fldPasswordEnabled Whether the password field is enabled.
 * @property fldPasswordError A list of validation errors for the password field.
 * @property btnProceedVisible Whether the "Proceed" button is visible.
 * @property btnProceedEnabled Whether the "Proceed" button is enabled.
 */
data class FormState(
    val fldFirstName: TextFieldState = TextFieldState(STR_EMPTY),
    val fldFirstNameEnabled: Boolean = true,
    val fldFirstNameError: List<UiText> = emptyList(),
    val fldLastName: TextFieldState = TextFieldState(STR_EMPTY),
    val fldLastNameEnabled: Boolean = true,
    val fldLastNameError: List<UiText> = emptyList(),
    val fldEmail: TextFieldState = TextFieldState(STR_EMPTY),
    val fldEmailEnabled: Boolean = true,
    val fldEmailError: List<UiText> = emptyList(),
    val fldPassword: TextFieldState = TextFieldState(STR_EMPTY),
    val fldPasswordEnabled: Boolean = true,
    val fldPasswordError: List<UiText> = emptyList(),
    val btnProceedVisible: Boolean = false,
    val btnProceedEnabled: Boolean = false
) {
    /** Validator for the first name field. */
    private val firstNameValidator = TxtFieldPersonNameValidation()
    /** Validator for the last name field. */
    private val lastNameValidator = TxtFieldPersonNameValidation()
    /** Validator for the email field. */
    private val emailValidator = TxtFieldEmailValidation()
    /** Validator for the password field. */
    private val passwordValidator = TxtFieldPasswordValidation()
    /**
     * Validates the given form fields and updates their error states.
     * @param firstName The new value for the first name field (defaults to the current value).
     * @param lastName The new value for the last name field (defaults to the current value).
     * @param email The new value for the email field (defaults to the current value).
     * @param password The new value for the password field (defaults to the current value).
     * @return A new `FormState` instance with updated validation results.
     */
    fun validateField(
        firstName: CharSequence = fldFirstName.text, lastName: CharSequence = fldLastName.text,
        email: CharSequence = fldEmail.text, password: CharSequence = fldPassword.text
    ) = copy(
        fldFirstName = TextFieldState(firstName.toString()),
        fldFirstNameError = firstNameValidator.validate(firstName.toString(), required = true).errorMessages,
        fldLastName = TextFieldState(lastName.toString()),
        fldLastNameError = lastNameValidator.validate(lastName.toString(), required = false).errorMessages,
        fldEmail = TextFieldState(email.toString()),
        fldEmailError = emailValidator.validate(email.toString(), required = true).errorMessages,
        fldPassword = TextFieldState(password.toString()),
        fldPasswordError = passwordValidator.validate(password.toString(), required = true).errorMessages
    ).validateFields()
    /**
     * Checks the form's validation state and updates button visibility and enabled state.
     *
     * @return A new `FormState` instance with updated button states.
     */
    fun validateFields() = copy(
        btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
            fldEmailError.isEmpty() && fldPasswordError.isEmpty(),
        btnProceedEnabled = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
            fldEmailError.isEmpty() && fldPasswordError.isEmpty()
    )
    /**
     * Disables all input fields and the "Proceed" button.
     *
     * @return A new `FormState` instance with all inputs disabled.
     */
    fun disableInputs() = copy(
        fldFirstNameEnabled = false,
        fldLastNameEnabled = false,
        fldEmailEnabled = false,
        fldPasswordEnabled = false,
        btnProceedEnabled = false
    )
}