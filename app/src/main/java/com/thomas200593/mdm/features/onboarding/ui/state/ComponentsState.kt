package com.thomas200593.mdm.features.onboarding.ui.state

import com.thomas200593.mdm.features.cnf_localization_language.entity.Language
import com.thomas200593.mdm.features.cnf_common.entity.Common
import com.thomas200593.mdm.features.onboarding.entity.Onboarding

sealed interface ComponentsState {
    data object Loading : ComponentsState
    data class Loaded(
        val confCommon: Common, val languages: List<Language>,
        val onboardingPages: List<Onboarding>,
        val currentIndex: Int, val maxIndex: Int
    ) : ComponentsState
}