package com.thomas200593.mdm.features.user_management.security.session.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UCValidateAndGet @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val ucArchiveAndCleanUp : UCArchiveAndCleanUp,
    private val repoSession : RepoSession
) {
    operator fun invoke() = repoSession.getCurrent().flowOn(ioDispatcher).map { result ->
        result.fold(
            onSuccess = { data ->
                if(repoSession.isValid(data.session).getOrDefault(false)) Result.success(data)
                else Result.failure(Error.Data.ValidationError(message = "Session validation failed"))
            },
            onFailure = { error -> Result.failure(error) }
        )
    }
    .onEach { if (it.isFailure) ucArchiveAndCleanUp.invoke() }
    .catch { exception -> ucArchiveAndCleanUp.invoke(); emit(Result.failure(Error.Database.DaoQueryError(message = exception.message, cause = exception))) }
}