package com.thomas200593.mdm.features.introduction.initialization.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldDatePickerValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormInitializationState(
    /** First name */
    val fldFirstName: String = STR_EMPTY,
    val fldFirstNameEnabled: Boolean = true,
    val fldFirstNameError: List<UiText> = emptyList(),
    /** Last name */
    val fldLastName: String = STR_EMPTY,
    val fldLastNameEnabled: Boolean = true,
    val fldLastNameError: List<UiText> = emptyList(),
    /** Date of Birth */
    val fldDateOfBirth: String = STR_EMPTY,
    val fldDateOfBirthEnabled: Boolean = true,
    val fldDateOfBirthError: List<UiText> = emptyList(),
    /** Email */
    val fldEmail: String = STR_EMPTY,
    val fldEmailEnabled: Boolean = true,
    val fldEmailError: List<UiText> = emptyList(),
    /** Password */
    val fldPassword: String = STR_EMPTY,
    val fldPasswordEnabled: Boolean = true,
    val fldPasswordError: List<UiText> = emptyList(),
    /** Checkbox */
    val fldChbToCEnabled: Boolean = true,
    val fldChbToCChecked: Boolean = false,
    /** Buttons */
    val btnProceedVisible: Boolean = false,
    val btnProceedEnabled: Boolean = false
) {
    private val canProceed get() = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
        fldDateOfBirthError.isEmpty() && fldEmailError.isEmpty() && fldPasswordError.isEmpty() &&
        (fldChbToCChecked == true)
    fun setValue(
        firstName: String? = null,
        lastName: String? = null,
        dateOfBirth: String? = null,
        email: String? = null,
        password: String? = null,
        chbToCChecked: Boolean? = null
    ) = copy(
        fldFirstName = firstName ?: fldFirstName,
        fldLastName = lastName ?: fldLastName,
        fldDateOfBirth = dateOfBirth ?: fldDateOfBirth,
        fldEmail = email ?: fldEmail,
        fldPassword = password ?: fldPassword,
        fldChbToCChecked = chbToCChecked ?: fldChbToCChecked
    )
    fun validateField(formField: FormField): FormInitializationState = when (formField) {
        FormField.FirstName -> copy(fldFirstNameError = firstNameValidator.validate(input = fldFirstName, required = true).errorMessages)
        FormField.LastName -> copy(fldLastNameError = lastNameValidator.validate(input = fldLastName, required = false).errorMessages)
        FormField.DateOfBirth -> copy(fldDateOfBirthError = dateOfBirthValidator.validate(input = fldDateOfBirth, required = true, minLength = -100, maxLength = 0).errorMessages)
        FormField.Email -> copy(fldEmailError = emailValidator.validate(input = fldEmail, required = true).errorMessages)
        FormField.Password -> copy(fldPasswordError = passwordValidator.validate(input = fldPassword, required = true).errorMessages)
        FormField.Checkbox -> this
    }
    fun validateFields() = copy(btnProceedVisible = canProceed, btnProceedEnabled = canProceed)
    fun disableInputs() = copy(
        fldFirstNameEnabled = false,
        fldLastNameEnabled = false,
        fldDateOfBirthEnabled = false,
        fldEmailEnabled = false,
        fldPasswordEnabled = false,
        fldChbToCEnabled = false,
        btnProceedEnabled = false
    )
    companion object {
        enum class FormField { FirstName, LastName, DateOfBirth, Email, Password, Checkbox }
        private val firstNameValidator = TxtFieldPersonNameValidation()
        private val lastNameValidator = TxtFieldPersonNameValidation()
        private val dateOfBirthValidator = TxtFieldDatePickerValidation()
        private val emailValidator = TxtFieldEmailValidation()
        private val passwordValidator = TxtFieldPasswordValidation()
    }
}