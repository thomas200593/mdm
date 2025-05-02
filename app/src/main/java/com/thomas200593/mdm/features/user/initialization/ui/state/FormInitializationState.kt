package com.thomas200593.mdm.features.user.initialization.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPersonNameValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormInitializationState(
    val fldFirstName: String = STR_EMPTY, val fldFirstNameEnabled: Boolean = true, val fldFirstNameError: List<UiText> = emptyList(),
    val fldLastName: String = STR_EMPTY, val fldLastNameEnabled: Boolean = true, val fldLastNameError: List<UiText> = emptyList(),
    val fldEmail: String = STR_EMPTY, val fldEmailEnabled: Boolean = true, val fldEmailError: List<UiText> = emptyList(),
    val fldPassword: String = STR_EMPTY, val fldPasswordEnabled: Boolean = true, val fldPasswordError: List<UiText> = emptyList(),
    val btnProceedVisible: Boolean = false, val btnProceedEnabled: Boolean = false
) {
    private val firstNameValidator = TxtFieldPersonNameValidation() ; private val lastNameValidator = TxtFieldPersonNameValidation()
    private val emailValidator = TxtFieldEmailValidation() ; private val passwordValidator = TxtFieldPasswordValidation()
    fun validateField(firstName: String = fldFirstName, lastName: String = fldLastName, email: String = fldEmail, password: String = fldPassword) = copy(
        fldFirstName = firstName, fldFirstNameError = firstNameValidator.validate(firstName, required = true).errorMessages,
        fldLastName = lastName, fldLastNameError = lastNameValidator.validate(lastName, required = false).errorMessages,
        fldEmail = email, fldEmailError = emailValidator.validate(email, required = true).errorMessages,
        fldPassword = password, fldPasswordError = passwordValidator.validate(password, required = true).errorMessages
    ).validateFields()
    fun validateFields() = copy(
        btnProceedVisible = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() && fldEmailError.isEmpty() && fldPasswordError.isEmpty(),
        btnProceedEnabled = fldFirstNameError.isEmpty() && fldLastNameError.isEmpty() && fldEmailError.isEmpty() && fldPasswordError.isEmpty()
    )
    fun disableInputs() = copy(fldFirstNameEnabled = false, fldLastNameEnabled = false, fldEmailEnabled = false, fldPasswordEnabled = false, btnProceedEnabled = false)
}