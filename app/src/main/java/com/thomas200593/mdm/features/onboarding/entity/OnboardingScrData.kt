package com.thomas200593.mdm.features.onboarding.entity

import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.common.entity.Common

data class OnboardingScrData(
    val confCommon: Common,
    val languageList: List<Language>,
    val onboardingPages: List<Onboarding>,
    val listCurrentIndex: Int = 0,
    val listMaxIndex: Int = 0
)