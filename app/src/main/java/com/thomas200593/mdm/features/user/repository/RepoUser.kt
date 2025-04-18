package com.thomas200593.mdm.features.user.repository

import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoUser {
    fun getOneByEmail(email: String) : Flow<Result<UserEntity>>
    suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity>
}
class RepoUserImpl @Inject constructor(
    private val daoUser: DaoUser
) : RepoUser {
    override fun getOneByEmail(email: String) : Flow<Result<UserEntity>> =
        runCatching { daoUser.getOneByEmail(email) }.getOrElse { return flowOf(Result.failure(it)) }
            .map { entity -> entity?.let { Result.success(it) } ?: Result.failure(NoSuchElementException("User not found with e-mail: $email")) }
    override suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity> =
        runCatching { daoUser.getOneByEmail(user.email).first() ?: user.takeIf { daoUser.insertUser(it) > 0 } ?: error("ErrorDialog creating user.") }
            .fold(
                onSuccess = { entity -> Result.success(entity) },
                onFailure = { t -> Result.failure(t) }
            )
}