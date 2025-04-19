package com.thomas200593.mdm.features.auth.repository

import androidx.room.Transaction
import com.thomas200593.mdm.core.design_system.security.hashing.BCrypt
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoAuth<T: AuthType> {
    suspend fun registerAuthLocalEmailPassword(authEntity: AuthEntity): Result<AuthEntity>
    fun getAuthByUser(user: UserEntity): Flow<Result<AuthEntity>>
}
class RepoAuthImpl @Inject constructor(
    private val daoAuth: DaoAuth,
    private val bCrypt: BCrypt
) : RepoAuth<AuthType> {
    @Transaction
    override suspend fun registerAuthLocalEmailPassword(authEntity: AuthEntity): Result<AuthEntity> = runCatching {
        daoAuth.deleteAuthByUserId(authEntity.userId)
        val hashedAuthEntity = authEntity.copy(authType = (authEntity.authType as AuthType.LocalEmailPassword)
            .copy(password = bCrypt.hash(authEntity.authType.password)))
        daoAuth.insertAuth(hashedAuthEntity)
        hashedAuthEntity
    }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(it) }
    )
    override fun getAuthByUser(user: UserEntity) =
        daoAuth.getAuthByUserId(userId = user.uid)
            .map { auth -> runCatching { requireNotNull(auth) { "Auth not found" } } }
            .catch { e -> emit(Result.failure(e)) }
}