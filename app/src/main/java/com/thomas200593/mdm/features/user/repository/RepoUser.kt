package com.thomas200593.mdm.features.user.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoUser {
    fun getOneByUid(uid: String) : Flow<Result<UserEntity>>
    fun getOneByEmail(email: String) : Flow<Result<UserEntity>>
    suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity>
}
class RepoUserImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUser: DaoUser
) : RepoUser {
    override fun getOneByUid(uid: String): Flow<Result<UserEntity>> = uid.takeIf { it.isNotBlank() }
        ?.let { validUser -> daoUser.getOneByUid(validUser).flowOn(ioDispatcher)
            .map { user -> user.firstOrNull()?.let { Result.success(it) } ?: Result.failure(NoSuchElementException("User not found!")) }
            .catch { emit(Result.failure(it)) }
        } ?: flowOf(Result.failure(IllegalArgumentException("Uid cannot be blank")))
    override fun getOneByEmail(email: String) : Flow<Result<UserEntity>> = email.takeIf { it.isNotBlank() }
        ?.let { validEmail -> daoUser.getOneByEmail(validEmail).flowOn(ioDispatcher)
            .map { user -> user.firstOrNull()?.let { Result.success(it) } ?: Result.failure(NoSuchElementException("User not found!")) }
            .catch { emit(Result.failure(it)) }
        } ?: flowOf(Result.failure(IllegalArgumentException("Email cannot be blank!")))
    override suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity> = runCatching {
        daoUser.getOneByEmail(user.email).flowOn(ioDispatcher).firstOrNull()?.firstOrNull() ?: user.takeIf { daoUser.insertUser(it) > 0 }
        ?: throw IllegalStateException("Error creating user!")
    }.fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
}