package com.thomas200593.mdm.features.initialization.repository

import androidx.room.Transaction
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.repository.RepoAuth
import com.thomas200593.mdm.features.user.repository.RepoUser
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createUserFromLocalEmailPassword(authType: AuthType.LocalEmailPassword): Result<AuthType.LocalEmailPassword>
}

class RepoInitializationImpl @Inject constructor(
    private val repoUser: RepoUser,
    private val repoAuth: RepoAuth<AuthType>
) : RepoInitialization {
    @Transaction
    override suspend fun createUserFromLocalEmailPassword(authTypeLocal: AuthType.LocalEmailPassword): Result<AuthType.LocalEmailPassword> {
        return try {
            //Step 1: Ensure User Exists
            val userResult = repoUser.getOrCreateUser(authTypeLocal)
            if(userResult.isFailure) return Result.failure(userResult.exceptionOrNull()!!)

            val user = userResult.getOrThrow()

            //Step 2: Create Auth method
            val authResult = repoAuth.registerAuth(
                user,
                authTypeLocal
            )
            if(authResult.isFailure) return Result.failure(authResult.exceptionOrNull()!!)
            else Result.success(authTypeLocal)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}