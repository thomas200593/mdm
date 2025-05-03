package com.thomas200593.mdm.features.common.cnf_common.entity

import com.thomas200593.mdm.features.common.cnf_localization.entity.Localization
import com.thomas200593.mdm.features.common.cnf_ui.entity.UI
import com.thomas200593.mdm.features.introduction.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.introduction.onboarding.entity.OnboardingStatus

data class Common(
    val ui: UI,
    val localization: Localization,
    val onboardingStatus: OnboardingStatus,
    val firstTimeStatus: FirstTimeStatus
)