package com.thomas200593.mdm.features.initial.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.common.repository.RepoConfCommon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetDataInitial @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatchers: CoroutineDispatcher,
    private val repoConfCommon: RepoConfCommon
) { operator fun invoke() = repoConfCommon.confCommon.flowOn(ioDispatchers) }