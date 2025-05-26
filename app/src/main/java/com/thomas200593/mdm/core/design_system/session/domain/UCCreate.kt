package com.thomas200593.mdm.core.design_system.session.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UCCreate @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoSession : RepoSession
) { suspend operator fun invoke(session : SessionEntity) = withContext (ioDispatcher) {
    repoSession.create(session) } }