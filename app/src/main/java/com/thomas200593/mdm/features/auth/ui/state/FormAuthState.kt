package com.thomas200593.mdm.features.auth.ui.state

import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.core.ui.component.text_field.state.UiText

data class FormAuthState(
    val fldEmail: String = STR_EMPTY, val fldEmailEnabled: Boolean = true, val fldEmailError: List<UiText> = emptyList(),
    val fldPassword: String = STR_EMPTY, val fldPasswordEnabled: Boolean = true, val fldPasswordError: List<UiText> = emptyList(),
    val btnSignInEnabled: Boolean = false
)