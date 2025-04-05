package com.thomas200593.mdm.features.onboarding.domain

import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.onboarding.entity.Onboarding

data class DTOOnboarding(
    val confCommon: Common,
    val languageList: List<Language>,
    val onboardingPages: List<Onboarding>,
    val listCurrentIndex: Int,
    val listMaxIndex: Int
)