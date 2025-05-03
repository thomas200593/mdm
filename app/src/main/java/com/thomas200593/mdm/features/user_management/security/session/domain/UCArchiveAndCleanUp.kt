package com.thomas200593.mdm.features.user_management.security.session.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionHistoryEntity
import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSession
import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSessionHistory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UCArchiveAndCleanUp @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoSession : RepoSession,
    private val repoSessionHistory: RepoSessionHistory
) { suspend operator fun invoke() = withContext (ioDispatcher) {
    repoSession.getAll().flowOn(ioDispatcher).first().getOrDefault(emptyList()).takeIf { it.isNotEmpty() }
        ?.map { SessionHistoryEntity(session = it) }?.let { repoSessionHistory.insertAll(it) }
    repoSession.deleteAll()
} }