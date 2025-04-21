package com.thomas200593.mdm.core.design_system.session.repository

import androidx.room.Transaction
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.dao.DaoSession
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionHistoryEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoSession {
    fun getCurrent(): Flow<Result<SessionEntity>>
    suspend fun isValid(session: SessionEntity): Result<Boolean>
    suspend fun delete()
    suspend fun archive()
}
class RepoSessionImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoSession: DaoSession
) : RepoSession {
    override fun getCurrent() = daoSession.getCurrentSession().flowOn(ioDispatcher)
        .catch { Result.failure<Throwable>(it) }.map { runCatching { requireNotNull(it) { "No session found" } } }
    override suspend fun isValid(session: SessionEntity) = withContext (ioDispatcher) { runCatching { (
        (UUIDv7.extractTimestamp(UUIDv7.fromUUIDString(session.sessionId)) > 0) &&
        (session.expiresAt?.let { it >= Constants.NOW_EPOCH_MILLISECOND } == true)) }
        .fold(onSuccess = { Result.success(true) }, onFailure = { Result.success(false) }) }
    override suspend fun delete() = withContext (ioDispatcher) { daoSession.deleteAll() }
    override suspend fun archive() = withContext (ioDispatcher) {
        val sessions = daoSession.getAll().filterNotNull().first().map { SessionHistoryEntity(session = it) }
        daoSession.insertAllSessionHistory(sessions)
    }
}