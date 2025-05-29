package com.thomas200593.mdm.features.role_selection.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.features.management.user.repository.RepoUser
import com.thomas200593.mdm.features.role_selection.entity.DTORoleSelection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCSetUserRole @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoUser : RepoUser,
    private val repoSession : RepoSession
) {
    suspend operator fun invoke(dto: DTORoleSelection) : Result<Any> {
        if(dto.user == null) return Result.failure(Error.Input.MalformedError(message = "Parameter user is empty."))
        if(dto.role == null) return Result.failure(Error.Input.MalformedError(message = "Parameter role is empty."))
        if(dto.session == null) return Result.failure(Error.Input.MalformedError(message = "Parameter session is empty."))
        return repoUser.getOneByEmail(dto.user.email).flowOn(ioDispatcher).first().let { result ->
            when {
                result.isFailure -> {
                    val error = result.exceptionOrNull()
                    return when(error) {
                        is Error.Database.DaoQueryNoDataError -> Result.failure(Error.Database.DaoQueryNoDataError(message = "User ${dto.user.email} not found.", cause = error.cause))
                        is Error.Database.DaoQueryError -> Result.failure(Error.Database.DaoQueryError(cause = error.cause))
                        else -> Result.failure(Error.UnknownError(message = error?.message, cause = error?.cause))
                    }
                }
                result.isSuccess -> {
                    return Result.success(1) /*TODO*/
                }
                else -> Result.failure(Error.UnknownError(message = "Unexpected result state"))
            }
        }
    }
}