package com.thomas200593.mdm.features.session.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.session.dao.DaoSessionHistory
import com.thomas200593.mdm.features.session.entity.SessionHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoSessionHistory {
    suspend fun insertAll(sessions : List<SessionHistoryEntity>)
}
class RepoSessionHistoryImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoSessionHistory: DaoSessionHistory
) : RepoSessionHistory {
    override suspend fun insertAll(sessions: List<SessionHistoryEntity>) = withContext (ioDispatcher) { daoSessionHistory.insertAll(sessions) }
}