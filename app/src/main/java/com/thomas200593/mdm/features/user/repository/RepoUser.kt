package com.thomas200593.mdm.features.user.repository

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.toUserEntity
import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import java.util.UUID
import javax.inject.Inject

interface RepoUser {
    suspend fun getOrCreateUser(
        authTypeLocal: AuthType.LocalEmailPassword,
        email: String
    ): Result<UserEntity>
}

class RepoUserImpl @Inject constructor(
    private val daoUser: DaoUser
) : RepoUser {
    override suspend fun getOrCreateUser(
        authTypeLocal: AuthType.LocalEmailPassword,
        email: String
    ): Result<UserEntity> {
        return try {
            // Get User
            daoUser.getUserByEmail(email)?.let{ existingUser -> return Result.success(existingUser) }
            // Or Create User
            val newUser = authTypeLocal.toUserEntity(
                uid = UUID.randomUUID().toString(),
                email = email
            )
            val newUserId = daoUser.insertUser(newUser)
            if(newUserId > 0) Result.success(newUser.copy(seqId = newUserId))
            else Result.failure(Exception("User Creation Failed"))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}