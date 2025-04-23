package com.thomas200593.mdm.core.design_system.session.domain

import androidx.compose.runtime.Stable
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.features.user.entity.UserEntity
import com.thomas200593.mdm.features.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Stable
class UCValidateAndGet @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val ucArchiveAndCleanUp: UCArchiveAndCleanUp,
    private val repoSession: RepoSession,
    private val repoUser: RepoUser
) { operator fun invoke() = repoSession.getCurrent().flowOn(ioDispatcher).map { result ->
    result.fold(
        onSuccess = { session -> session?.takeIf { repoSession.isValid(it).getOrDefault(false) }
            ?.let { validSession -> repoUser.getOneByUid(validSession.userId).first()
                .fold(onSuccess = { Result.success(validSession to it) }, onFailure = { Result.failure(it) }) }
            ?: Result.failure<Pair<SessionEntity, UserEntity>>(Throwable("Session Invalid or Not Found")) },
        onFailure = { Result.failure<Pair<SessionEntity, UserEntity>>(it) }
    ) }.catch { ucArchiveAndCleanUp.invoke(); emit(Result.failure<Pair<SessionEntity, UserEntity>>(it)) } }