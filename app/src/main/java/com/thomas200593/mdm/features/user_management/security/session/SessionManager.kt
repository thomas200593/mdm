package com.thomas200593.mdm.features.user_management.security.session

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.user_management.security.session.domain.UCArchiveAndCleanUp
import com.thomas200593.mdm.features.user_management.security.session.domain.UCCreate
import com.thomas200593.mdm.features.user_management.security.session.domain.UCValidateAndGet
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionEntity
import com.thomas200593.mdm.features.user_management.security.session.entity.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface SessionManager {
    val currentSession: Flow<SessionState>
    suspend fun archiveAndCleanUpSession()
    suspend fun startSession(sessionEntity: SessionEntity): Result<SessionEntity>
}
class SessionManagerImpl @Inject constructor (
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ucValidateAndGet: UCValidateAndGet,
    private val ucArchiveAndCleanUp: UCArchiveAndCleanUp,
    private val ucCreate: UCCreate
) : SessionManager {
    override val currentSession = ucValidateAndGet.invoke().flowOn(ioDispatcher)
        .map {
            it.fold(
                onSuccess = { SessionState.Valid(it) },
                onFailure = { err ->
                    val error = err as? Error ?: Error.UnknownError(message = err.message, cause = err.cause)
                    SessionState.Invalid(error)
                }
            )
        }
        .onStart { emit(SessionState.Loading) }
    override suspend fun archiveAndCleanUpSession() = ucArchiveAndCleanUp.invoke()
    override suspend fun startSession(session: SessionEntity) = ucCreate.invoke(session = session)
}