package com.thomas200593.mdm.features.auth.repository

import com.thomas200593.mdm.features.auth.entity.AuthResult
import com.thomas200593.mdm.features.auth.entity.AuthType
import kotlin.Result

class RepoAuthLocalEmailPassword : RepoAuth<AuthType.LocalEmailPassword>{
    override suspend fun authenticate(authType: AuthType.LocalEmailPassword): Result<AuthResult> {
        return Result.success(
            AuthResult(
                authType = authType,
                email = "",
                displayName = "",
                sessionToken = ""
            )
        )
    }
}