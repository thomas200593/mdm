package com.thomas200593.mdm.features.management.user.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.user.dao.DaoUser
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoUser {
    suspend fun deleteAll()
    fun getOneByUid(uid : String) : Flow<Result<UserEntity>>
    fun getOneByEmail(email : String) : Flow<Result<UserEntity>>
}
class RepoUserImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUser: DaoUser
) : RepoUser {
    override suspend fun deleteAll() = withContext (ioDispatcher) { daoUser.deleteAll() }
    override fun getOneByUid(uid: String): Flow<Result<UserEntity>> = uid.takeIf { it.isNotBlank() }
        ?. let { validUser -> daoUser.getOneByUid(validUser).flowOn(ioDispatcher)
            .map { user -> user.firstOrNull()
                ?. let { Result.success(it) }
                ?: Result.failure(Error.Database.DaoQueryNoDataError(message = "User not found with UID : $uid")) }
            .catch { emit(Result.failure(Error.Database.DaoQueryError(cause = it))) } }
        ?: flowOf(Result.failure(Error.Input.MalformedError(message = "UID Cannot be blank!")))
    override fun getOneByEmail(email: String) : Flow<Result<UserEntity>> = email.takeIf { it.isNotBlank() }
        ?. let { validEmail -> daoUser.getOneByEmail(validEmail).flowOn(ioDispatcher)
            .map { user -> user.firstOrNull()
                ?. let { Result.success(it) }
                ?: Result.failure(Error.Database.DaoQueryNoDataError(message = "User not found with email : $email")) }
            .catch { emit(Result.failure(Error.Database.DaoQueryError(cause = it))) } }
        ?: flowOf(Result.failure(Error.Input.MalformedError(message = "Email cannot be blank!")))
}