package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.entity.toAuthEntity
import com.thomas200593.mdm.features.initialization.entity.toUserEntity
import com.thomas200593.mdm.features.initialization.entity.toUserProfileEntity
import com.thomas200593.mdm.features.initialization.entity.toUserRoleEntity
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import com.thomas200593.mdm.features.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoInitialization: RepoInitialization,
    private val repoUser: RepoUser
) {
    suspend operator fun invoke(dto : DTOInitialization) : Result<DTOInitialization> {
        if(dto.initialSetOfRoles.isEmpty()) return Result.failure(Error.Input.MalformedError(message = "Initial user cannot have no role(s)"))
        return repoUser.getOneByEmail(dto.email).first().let {
            when {
                it.isSuccess -> Result.failure(Error.Data.DuplicateError(message = "User with email ${dto.email} already exists"))
                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    when (error) {
                        is Error.Database.DaoQueryNoDataError -> when(dto.authType) {
                            is AuthType.LocalEmailPassword -> {
                                val user = dto.toUserEntity(uid = UUIDv7.generateAsString())
                                val auth = dto.toAuthEntity(uid = user.uid)
                                val profile = dto.toUserProfileEntity(uid = user.uid)
                                val roles = dto.toUserRoleEntity(uid = user.uid, roles = dto.initialSetOfRoles)
                                /*TODO*/ Result.success(dto)
                            }
                        }
                        is Error.Database.DaoQueryError -> Result.failure(Error.Database.DaoQueryError(cause = error.cause))
                        else -> Result.failure(Error.UnknownError(message = error?.message, cause = error?.cause))
                    }
                }
                else -> Result.failure(Error.UnknownError(message = "Unexpected result state"))
            }
        }
    }
    /*suspend operator fun invoke(dto : DTOInitialization) : Result<DTOInitialization> {
        if(dto.initialSetOfRoles.isEmpty()) return Result.failure(Error.Input.MalformedError(message = "Initial user cannot have 0 roles"))
        return when(dto.authType) {
            is AuthType.LocalEmailPassword -> withContext (ioDispatcher) {
                val result = repoInitialization.createUserLocalEmailPassword(dto).fold(
                    onSuccess = { repoInitialization.updateFirstTimeStatus(FirstTimeStatus.NO) ; Result.success(it) },
                    onFailure = { it.printStackTrace() ; Result.failure(it) }
                )
                result
            }
        }
    }*/
}