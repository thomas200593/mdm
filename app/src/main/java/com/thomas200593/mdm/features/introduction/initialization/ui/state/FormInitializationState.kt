package com.thomas200593.mdm.features.introduction.initialization.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormInitializationState(
    val fldFirstName: String = STR_EMPTY,
    val fldFirstNameEnabled: Boolean = true,
    val fldFirstNameError: List<UiText> = emptyList(),

    val fldLastName: String = STR_EMPTY,
    val fldLastNameEnabled: Boolean = true,
    val fldLastNameError: List<UiText> = emptyList(),

    val fldDateOfBirth: String = STR_EMPTY,
    val fldDateOfBirthEnabled: Boolean = true,
    val fldDateOfBirthError: List<UiText> = emptyList(),

    val fldEmail: String = STR_EMPTY,
    val fldEmailEnabled: Boolean = true,
    val fldEmailError: List<UiText> = emptyList(),

    val fldPassword: String = STR_EMPTY,
    val fldPasswordEnabled: Boolean = true,
    val fldPasswordError: List<UiText> = emptyList(),

    val fldChbToCEnabled: Boolean = true,
    val fldChbToCChecked: Boolean = false,

    val btnProceedVisible: Boolean = false,
    val btnProceedEnabled: Boolean = false
) {
    private val firstNameValidator = TxtFieldPersonNameValidation()
    private val lastNameValidator = TxtFieldPersonNameValidation()
    private val emailValidator = TxtFieldEmailValidation()
    private val passwordValidator = TxtFieldPasswordValidation()

    fun validateField(
        firstName: String = fldFirstName,
        lastName: String = fldLastName,
        email: String = fldEmail,
        password: String = fldPassword,
        chbToCChecked: Boolean = fldChbToCChecked
    ) = copy(
        fldFirstName = firstName,
        fldFirstNameError = firstNameValidator.validate(
            firstName,
            required = true
        ).errorMessages,

        fldLastName = lastName,
        fldLastNameError = lastNameValidator.validate(
            input = lastName,
            required = false
        ).errorMessages,

        fldEmail = email,
        fldEmailError = emailValidator.validate(
            input = email,
            required = true
        ).errorMessages,

        fldPassword = password,
        fldPasswordError = passwordValidator.validate(
            input = password, required = true
        ).errorMessages,

        fldChbToCChecked = chbToCChecked
    ).validateFields()
    fun validateFields() = copy(
        btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
                fldEmailError.isEmpty() && fldPasswordError.isEmpty() && fldChbToCChecked == true,
        btnProceedEnabled = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
                fldEmailError.isEmpty() && fldPasswordError.isEmpty() && fldChbToCChecked == true
    )
    fun disableInputs() = copy(
        fldFirstNameEnabled = false,
        fldLastNameEnabled = false,
        fldEmailEnabled = false,
        fldPasswordEnabled = false,
        fldChbToCEnabled = false,
        btnProceedEnabled = false
    )
}