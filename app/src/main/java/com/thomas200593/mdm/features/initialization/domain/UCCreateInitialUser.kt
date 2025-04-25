package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoInitialization: RepoInitialization
) { suspend operator fun invoke(dto : DTOInitialization) : Result<DTOInitialization> = when(dto.authType) {
    is AuthType.LocalEmailPassword -> withContext (ioDispatcher) {
        val result = repoInitialization.createUserLocalEmailPassword(dto).fold(
            onSuccess = { repoInitialization.updateFirstTimeStatus(FirstTimeStatus.NO) ; Result.success(it) },
            onFailure = { Result.failure(it) }
        )
        result
    }
} }