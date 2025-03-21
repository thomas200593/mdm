package com.thomas200593.mdm.features.user.repository

import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import javax.inject.Inject

interface RepoUser {
    suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity>
}

class RepoUserImpl @Inject constructor(
    private val daoUser: DaoUser
) : RepoUser {
    override suspend fun getOrCreateUser(user: UserEntity) : Result<UserEntity> =
        runCatching { daoUser.getUserByEmail(user.email) ?: user.takeIf { daoUser.insertUser(it) > 0 } ?: error("Error creating user.") }
            .fold(
                onSuccess = { entity -> Result.success(entity) },
                onFailure = { t -> Result.failure(t) }
            )
}