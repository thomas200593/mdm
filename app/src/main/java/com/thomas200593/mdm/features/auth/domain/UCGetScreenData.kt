package com.thomas200593.mdm.features.auth.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.common.cnf_common.domain.UCGetConfCommonCurrent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetScreenData @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetConfCommonCurrent: UCGetConfCommonCurrent
) { operator fun invoke() = ucGetConfCommonCurrent.invoke().flowOn(ioDispatcher) }