package com.thomas200593.mdm.features.conf.common.entity

import com.thomas200593.mdm.features.conf._localization.entity.Localization
import com.thomas200593.mdm.features.conf._ui.entity.UI
import com.thomas200593.mdm.features.initial.entity.FirstTimeStatus
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus

data class Common(
    val ui: UI,
    val localization: Localization,
    val firstTimeStatus: FirstTimeStatus,
    val onboardingStatus: OnboardingStatus
)