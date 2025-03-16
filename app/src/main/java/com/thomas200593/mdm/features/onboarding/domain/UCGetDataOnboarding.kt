package com.thomas200593.mdm.features.onboarding.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.common.domain.UCGetConfCommonCurrent
import com.thomas200593.mdm.features.onboarding.repository.RepoOnboarding
import com.thomas200593.mdm.features.onboarding.ui.VMOnboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetDataOnboarding @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetConfCommonCurrent: UCGetConfCommonCurrent,
    private val repoConfLanguage: RepoConfLanguage,
    private val repoOnboarding: RepoOnboarding
) {
    operator fun invoke() = combine(
        flow = ucGetConfCommonCurrent.invoke(),
        flow2 = repoConfLanguage.list(),
        flow3 = repoOnboarding.list
    ) { confCommon, languageList, onboardingPages ->
        VMOnboarding.ScrData(
            confCommon = confCommon,
            languageList = languageList,
            onboardingPages = onboardingPages,
            listCurrentIndex = 0,
            listMaxIndex = onboardingPages.size.minus(1)
        )
    }.flowOn(ioDispatcher)
}