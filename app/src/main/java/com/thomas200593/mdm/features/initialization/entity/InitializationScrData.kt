package com.thomas200593.mdm.features.initialization.entity

import com.thomas200593.mdm.core.ui.component.text_field._state.UiText
import com.thomas200593.mdm.features.conf.common.entity.Common

data class InitializationScrData(
    val confCommon: Common,
    val email: String = "",
    val emailError: List<UiText?> = emptyList<UiText>(),
    val password: String = "",
    val passwordError: List<UiText?> = emptyList<UiText>()
)