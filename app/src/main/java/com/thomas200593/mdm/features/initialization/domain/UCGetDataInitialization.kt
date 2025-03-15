package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.common.domain.UCGetConfCommonCurrent
import com.thomas200593.mdm.features.initialization.ui.VMInitialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetDataInitialization @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetConfCommonCurrent: UCGetConfCommonCurrent,
    private val repoConfLanguage: RepoConfLanguage
) {
    operator fun invoke() = combine(
        flow = ucGetConfCommonCurrent.invoke(),
        flow2 = repoConfLanguage.list()
    ) { confCommon, languageList ->
        VMInitialization.ScrData(
            confCommon = confCommon,
            languageList = languageList,
            form = VMInitialization.Form()
        )
    }.flowOn(ioDispatcher)
}