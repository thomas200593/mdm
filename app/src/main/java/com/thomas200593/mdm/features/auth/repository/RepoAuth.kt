package com.thomas200593.mdm.features.auth.repository

import com.thomas200593.mdm.features.auth.entity.AuthResult
import com.thomas200593.mdm.features.auth.entity.AuthType

interface RepoAuth<T: AuthType> {
    suspend fun authenticate(authType: T): Result<AuthResult>
}