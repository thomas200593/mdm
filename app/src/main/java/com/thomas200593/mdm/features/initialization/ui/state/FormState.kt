package com.thomas200593.mdm.features.initialization.ui.state

import androidx.compose.foundation.text.input.TextFieldState
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

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
    private val firstNameValidator = TxtFieldPersonNameValidation()
    private val lastNameValidator = TxtFieldPersonNameValidation()
    private val emailValidator = TxtFieldEmailValidation()
    private val passwordValidator = TxtFieldPasswordValidation()
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
    fun validateFields() = copy(
        btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
            fldEmailError.isEmpty() && fldPasswordError.isEmpty(),
        btnProceedEnabled = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() &&
            fldEmailError.isEmpty() && fldPasswordError.isEmpty()
    )
    fun disableInputs() = copy(
        fldFirstNameEnabled = false,
        fldLastNameEnabled = false,
        fldEmailEnabled = false,
        fldPasswordEnabled = false,
        btnProceedEnabled = false
    )
}