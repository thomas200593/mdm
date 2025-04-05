package com.thomas200593.mdm.features.auth.repository

import androidx.room.Transaction
import com.thomas200593.mdm.core.design_system.security.hashing.BCrypt
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import javax.inject.Inject

interface RepoAuth<T: AuthType> {
    suspend fun registerAuthLocalEmailPassword(authEntity: AuthEntity): Result<AuthEntity>
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
}