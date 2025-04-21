package com.thomas200593.mdm.core.design_system.session

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.domain.UCArchiveAndCleanUp
import com.thomas200593.mdm.core.design_system.session.domain.UCCreate
import com.thomas200593.mdm.core.design_system.session.domain.UCValidateAndGet
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface SessionManager {
    val validateAndGet : Flow<SessionState.Valid>
    suspend fun archiveAndCleanUp()
    suspend fun createSession(sessionEntity: SessionEntity): Result<SessionEntity>
}
class SessionManagerImpl @Inject constructor (
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ucValidateAndGet: UCValidateAndGet,
    private val ucArchiveAndCleanUp: UCArchiveAndCleanUp,
    private val ucCreate: UCCreate
) : SessionManager {
    override val validateAndGet = ucValidateAndGet.invoke().flowOn(ioDispatcher).onStart { SessionState.Loading }
        .catch { SessionState.Invalid(it) }.map { SessionState.Valid(it) }
    override suspend fun archiveAndCleanUp() = ucArchiveAndCleanUp.invoke()
    override suspend fun createSession(sessionEntity: SessionEntity) = ucCreate.invoke(sessionEntity = sessionEntity)
}