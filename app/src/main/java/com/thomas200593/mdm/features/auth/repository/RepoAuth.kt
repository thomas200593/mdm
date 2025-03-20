package com.thomas200593.mdm.features.auth.repository

import androidx.room.Transaction
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.user.entity.UserEntity
import javax.inject.Inject

interface RepoAuth<T: AuthType> {
    suspend fun registerAuth(user: UserEntity, authType: AuthType.LocalEmailPassword): Result<AuthEntity>
}

class RepoAuthImpl @Inject constructor(
    private val daoAuth: DaoAuth
) : RepoAuth<AuthType> {
    @Transaction
    override suspend fun registerAuth(
        user: UserEntity,
        authType: AuthType.LocalEmailPassword,
    ) : Result<AuthEntity> {
        return try {
            val existingAuth = daoAuth.getAuthByUserIdAndType(user.seqId, authType)
            if(existingAuth != null) {
                return Result.success(existingAuth)
            }
            val authEntity = AuthEntity(
                userId = user.seqId,
                authType = authType
            )
            daoAuth.insertAuth(authEntity)
            Result.success(authEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}