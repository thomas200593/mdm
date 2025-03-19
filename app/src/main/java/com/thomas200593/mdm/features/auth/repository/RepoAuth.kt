package com.thomas200593.mdm.features.auth.repository

import com.thomas200593.mdm.features.auth.entity.AuthResult
import com.thomas200593.mdm.features.auth.entity.AuthType

interface RepoAuth<T: AuthType> {
    suspend fun authenticate(authType: T): Result<AuthResult>
}

class RepoAuthImpl : RepoAuth<AuthType> {
    override suspend fun authenticate(authType: AuthType) : Result<AuthResult> = when(authType) {
        is AuthType.LocalEmailPassword -> {
            Result.success(
                AuthResult(
                    authType = AuthType.LocalEmailPassword(
                        provider = authType.provider,
                        email = authType.email,
                        password = authType.password
                    ),
                    email = "",
                    displayName = "Display Name",
                    sessionToken = ""
                )
            )
        }
        is AuthType.OAuth -> {
            Result.success(
                AuthResult(
                    authType = AuthType.OAuth(
                        provider = authType.provider,
                        token = authType.token
                    ),
                    email = "",
                    displayName = "",
                    sessionToken = ""
                )
            )
        }
    }
}