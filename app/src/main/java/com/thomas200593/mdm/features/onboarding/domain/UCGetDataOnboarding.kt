package com.thomas200593.mdm.features.onboarding.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import javax.inject.Inject

class UCGetDataOnboarding @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatchers: CoroutineDispatchers
) {
    operator fun invoke() {/*TODO*/}
}