package com.thomas200593.mdm.features.initialization.repository

import androidx.room.Transaction
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.repository.RepoAuth
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.entity.toAuthEntity
import com.thomas200593.mdm.features.initialization.entity.toUserEntity
import com.thomas200593.mdm.features.user.repository.RepoUser
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization>
}

class RepoInitializationImpl @Inject constructor(
    private val repoUser: RepoUser,
    private val repoAuth: RepoAuth<AuthType>
) : RepoInitialization {
    @Transaction
    override suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization> =
        repoUser.getOrCreateUser(dto.toUserEntity(UUIDv7.generateAsString())).fold(
            onSuccess = { user ->
                repoAuth.registerAuthLocalEmailPassword(dto.toAuthEntity(user.uid)).fold(
                    onSuccess = { Result.success(dto) },
                    onFailure = { Result.failure(it) }
                )
            },
            onFailure = { Result.failure(it) }
        )
}