package com.thomas200593.mdm.core.design_system.session.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.session.dao.DaoSession
import com.thomas200593.mdm.core.design_system.session.entity.DTOSessionUserData
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoSession {
    fun getAll() : Flow<Result<List<SessionEntity>>>
    fun getCurrent(): Flow<Result<DTOSessionUserData>>
    suspend fun isValid(session : SessionEntity) : Result<Boolean>
    suspend fun deleteAll()
    suspend fun create(sessionEntity : SessionEntity) : Result<SessionEntity>
}
class RepoSessionImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoSession: DaoSession
) : RepoSession {
    override fun getAll() = daoSession.getAll().flowOn(ioDispatcher).map { Result.success(it) }
        .catch { emit(Result.failure(Error.Database.DaoQueryError(message = it.message, cause = it.cause))) }
    override fun getCurrent() = daoSession.getCurrent().flowOn(ioDispatcher).map {
        if(it.isNotEmpty()) Result.success(it.first())
        else Result.failure(Error.Database.DaoQueryNoDataError(message = "No session found!"))
    }.catch { emit(Result.failure(Error.Database.DaoQueryError(message = it.message, cause = it.cause))) }
    override suspend fun isValid(session: SessionEntity) = withContext (ioDispatcher) {
        runCatching { UUIDv7.extractTimestamp(UUIDv7.fromUUIDString(session.sessionId)) > 0 && session.expiresAt
            ?.let { it >= Constants.NOW_EPOCH_SECOND } == true }
    }
    override suspend fun deleteAll() = withContext (ioDispatcher) { daoSession.deleteAll() }
    override suspend fun create(sessionEntity: SessionEntity): Result<SessionEntity> = withContext (ioDispatcher) {
        runCatching { daoSession.create(sessionEntity) }.fold(
            onSuccess = { Result.success(sessionEntity) },
            onFailure = { Result.failure(Error.Database.DaoInsertError(message = it.message, cause = it.cause))}
        )
    }
}