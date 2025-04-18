package com.thomas200593.mdm.features.auth.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormAuthState(
    val fldEmail: String = STR_EMPTY, val fldEmailEnabled: Boolean = true, val fldEmailError: List<UiText> = emptyList(),
    val fldPassword: String = STR_EMPTY, val fldPasswordEnabled: Boolean = true, val fldPasswordError: List<UiText> = emptyList(),
    val btnSignInEnabled: Boolean = false
) {
    private val emailValidator = TxtFieldEmailValidation(); private val passwordValidator = TxtFieldPasswordValidation()
    fun validateField(email: String = fldEmail, password: String = fldPassword) = copy(
        fldEmail = email, fldEmailError = emailValidator.validate(email, required = true).errorMessages,
        fldPassword = password, fldPasswordError = passwordValidator.validate(password, required = true, regex = Regex("^.{6,200}$")).errorMessages
    ).validateFields()
    fun validateFields() = copy(btnSignInEnabled = fldEmailError.isEmpty() && fldPasswordError.isEmpty())
    fun disableInputs() = copy(fldEmailEnabled = false, fldPasswordEnabled = false, btnSignInEnabled = false)
}