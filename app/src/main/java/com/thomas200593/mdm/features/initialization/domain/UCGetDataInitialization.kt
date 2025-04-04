package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.common.domain.UCGetConfCommonCurrent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Use case for retrieving initialization data.
 *
 * This class fetches common configuration data using [UCGetConfCommonCurrent] and ensures the operation
 * is executed on the appropriate coroutine dispatcher.
 *
 * @param ioDispatcher The [CoroutineDispatcher] for executing the operation on the IO thread.
 * @param ucGetConfCommonCurrent The use case responsible for retrieving the current common configuration.
 */
class UCGetDataInitialization @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetConfCommonCurrent: UCGetConfCommonCurrent
) {
    /**
     * Retrieves the current configuration data as a flow.
     * @return A cold flow emitting the latest common configuration, executed on the IO dispatcher.
     */
    operator fun invoke() = ucGetConfCommonCurrent.invoke().flowOn(ioDispatcher)
}