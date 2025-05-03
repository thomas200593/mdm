package com.thomas200593.mdm.features.introduction.onboarding.ui.state

import com.thomas200593.mdm.features.common.cnf_localization_language.entity.Language
import com.thomas200593.mdm.features.common.cnf_common.entity.Common
import com.thomas200593.mdm.features.introduction.onboarding.entity.Onboarding

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common, val languages: List<Language>,
        val onboardingPages: List<Onboarding>,
        val currentIndex: Int, val maxIndex: Int
    ) : ComponentsState
}