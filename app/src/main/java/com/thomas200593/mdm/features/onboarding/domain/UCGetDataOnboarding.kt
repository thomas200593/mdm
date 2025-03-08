package com.thomas200593.mdm.features.onboarding.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.common.repository.RepoConfCommon
import com.thomas200593.mdm.features.onboarding.entity.OnboardingScrData
import com.thomas200593.mdm.features.onboarding.repository.RepoOnboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetDataOnboarding @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoConfCommon: RepoConfCommon,
    private val repoConfLanguage: RepoConfLanguage,
    private val repoOnboarding: RepoOnboarding
) {
    operator fun invoke() = combine(
        flow = repoConfCommon.confCommon,
        flow2 = repoConfLanguage.list(),
        flow3 = repoOnboarding.list
    ) { confCommon, languageList, onboardingPages ->
        OnboardingScrData(
            confCommon = confCommon,
            languageList = languageList,
            onboardingPages = onboardingPages
        )
    }.flowOn(ioDispatcher)
}