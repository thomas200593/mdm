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
    override suspend fun createUserLocalEmailPassword(dto: DTOInitialization) : Result<DTOInitialization> {
        return try {
            //Step 1: Ensure User Exists
            val userResult = repoUser.getOrCreateUser(dto.toUserEntity(UUIDv7.generateAsString()))
            if(userResult.isFailure) return Result.failure(userResult.exceptionOrNull()!!)
            val user = userResult.getOrThrow()
            //Step 2: Create Auth method
            val authResult = repoAuth.registerAuthLocalEmailPassword(dto.toAuthEntity(user.uid))
            if(authResult.isFailure) return Result.failure(authResult.exceptionOrNull()!!)
            else Result.success(dto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}