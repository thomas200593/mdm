package com.thomas200593.mdm.features.introduction.initialization.domain

import android.database.sqlite.SQLiteAbortException
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.security.hashing.BCrypt
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.introduction.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.introduction.initialization.entity.toAuthEntity
import com.thomas200593.mdm.features.introduction.initialization.entity.toUserEntity
import com.thomas200593.mdm.features.introduction.initialization.entity.toUserProfileEntity
import com.thomas200593.mdm.features.introduction.initialization.entity.toUserRoleEntity
import com.thomas200593.mdm.features.introduction.initialization.repository.RepoInitialization
import com.thomas200593.mdm.features.user_management.security.auth.entity.AuthType
import com.thomas200593.mdm.features.user_management.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val bCrypt : BCrypt,
    private val repoUser : RepoUser,
    private val repoInitialization : RepoInitialization
) {
    suspend operator fun invoke(dto: DTOInitialization): Result<DTOInitialization> {
        if (dto.initialSetOfRoles.isEmpty()) return Result.failure(Error.Input.MalformedError(message = "Initial user cannot have no role(s)"))
        return repoUser.getOneByEmail(dto.email).flowOn(ioDispatcher).first().let { result ->
            when {
                result.isSuccess -> Result.failure(Error.Data.DuplicateError(message = "User with email ${dto.email} already exists"))
                result.isFailure -> {
                    val error = result.exceptionOrNull()
                    return when (error) {
                        is Error.Database.DaoQueryNoDataError -> when (dto.authType) {
                            is AuthType.LocalEmailPassword -> {
                                val input = dto.copy(authType = dto.authType.copy(password = bCrypt.hash(dto.authType.password)))
                                val user = input.toUserEntity(uid = UUIDv7.generateAsString())
                                val auth = input.toAuthEntity(uid = user.uid)
                                val profile = input.toUserProfileEntity(uid = user.uid)
                                val roles = input.toUserRoleEntity(uid = user.uid, roles = dto.initialSetOfRoles)
                                val insertionResult = repoInitialization.createUserLocalEmailPassword(
                                    user = user, auth = auth, profile = profile, roles = roles
                                )
                                val success = insertionResult.userId > 0 && insertionResult.profileId > 0 &&
                                    insertionResult.authId > 0 && insertionResult.rolesIds.all { it > 0 }
                                if (success) {
                                    repoInitialization.updateFirstTimeStatus(FirstTimeStatus.NO)
                                    Result.success(input)
                                } else {
                                    repoInitialization.rollback(user)
                                    Result.failure(Error.Database.DaoInsertError(
                                        message = "Initialization failed for user ${user.email}, rolling back",
                                        cause = SQLiteAbortException()
                                    ))
                                }
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
}