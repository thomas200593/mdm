package com.thomas200593.mdm.features.cnf_common.entity

import com.thomas200593.mdm.features.cnf_localization.entity.Localization
import com.thomas200593.mdm.features.cnf_ui.entity.UI
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus

data class Common(
    val ui: UI,
    val localization: Localization,
    val onboardingStatus: OnboardingStatus,
    val firstTimeStatus: FirstTimeStatus
)