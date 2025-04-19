package com.thomas200593.mdm.features.user.repository

import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
    override fun getOneByEmail(email: String) : Flow<Result<UserEntity>> = email.takeIf { it.isNotBlank() }
        ?.let { validEmail -> daoUser.getOneByEmail(validEmail)
            .map { user -> runCatching { requireNotNull(user) { "User not found with email: $validEmail" } } }
            .catch { e -> emit(Result.failure(e)) }
        } ?: flowOf(Result.failure(IllegalArgumentException("Email cannot be blank")))
    override suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity> =
        runCatching { daoUser.getOneByEmail(user.email).first() ?: user.takeIf { daoUser.insertUser(it) > 0 } ?: error("ErrorDialog creating user.") }
            .fold(
                onSuccess = { entity -> Result.success(entity) },
                onFailure = { t -> Result.failure(t) }
            )
}