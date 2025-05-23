package com.thomas200593.mdm.features.user_management.security.auth.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldEmailValidation
import com.thomas200593.mdm.core.ui.component.text_field.domain.TxtFieldPasswordValidation
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormAuthState(
    /** Auth Type */
    val formAuthType: FormAuthTypeState? = null,
    /** Email */
    val fldEmail: String = STR_EMPTY,
    val fldEmailEnabled: Boolean = true,
    val fldEmailError: List<UiText> = emptyList(),
    /** Password */
    val fldPassword: String = STR_EMPTY,
    val fldPasswordEnabled: Boolean = true,
    val fldPasswordError: List<UiText> = emptyList(),
    /** Buttons */
    val btnSignInEnabled: Boolean = false
) {
    private val canProceed get() = fldEmailError.isEmpty() && fldPasswordError.isEmpty()
    fun setValue(
        email: String? = null,
        password: String? = null
    ) = copy(
        fldEmail = email ?: fldEmail,
        fldPassword = password ?: fldPassword
    )
    fun validateField(formField: FormField) = when (formField) {
        FormField.Email -> copy(fldEmailError = emailValidator.validate(input = fldEmail, required = true).errorMessages)
        FormField.Password -> copy(fldPasswordError = passwordValidator.validate(input = fldPassword, required = true, regex = Regex("^.{6,200}$")).errorMessages)
    }
    fun validateFields() = copy(btnSignInEnabled = canProceed)
    fun disableInputs() = copy(
        fldEmailEnabled = false,
        fldPasswordEnabled = false,
        btnSignInEnabled = false
    )
    companion object {
        enum class FormField { Email, Password }
        private val emailValidator = TxtFieldEmailValidation()
        private val passwordValidator = TxtFieldPasswordValidation()
    }
}