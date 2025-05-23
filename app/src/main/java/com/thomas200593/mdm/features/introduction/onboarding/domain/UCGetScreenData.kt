package com.thomas200593.mdm.features.introduction.onboarding.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.common.cnf_localization_language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.common.cnf_common.domain.UCGetConfCommonCurrent
import com.thomas200593.mdm.features.introduction.onboarding.repository.RepoOnboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetScreenData @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val ucGetConfCommonCurrent : UCGetConfCommonCurrent,
    private val repoConfLanguage : RepoConfLanguage,
    private val repoOnboarding : RepoOnboarding
) {
    operator fun invoke() = combine(
        flow = ucGetConfCommonCurrent.invoke().flowOn(ioDispatcher),
        flow2 = repoConfLanguage.list().flowOn(ioDispatcher),
        flow3 = repoOnboarding.list.flowOn(ioDispatcher)
    ) { confCommon, languageList, onboardingPages -> Triple(confCommon, languageList, onboardingPages) }
        .flowOn(ioDispatcher)
}