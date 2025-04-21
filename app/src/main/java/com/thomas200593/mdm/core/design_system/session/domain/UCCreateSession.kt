package com.thomas200593.mdm.core.design_system.session.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UCCreateSession @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoSession : RepoSession
) { operator fun invoke() {} }