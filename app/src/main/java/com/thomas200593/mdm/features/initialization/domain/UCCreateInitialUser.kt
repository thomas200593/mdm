package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    private val repoInitialization: RepoInitialization
) {
    suspend operator fun invoke(
        authType: AuthType,
        email: String
    ) = when(authType) {
        is AuthType.LocalEmailPassword -> repoInitialization.createUserFromLocalEmailPassword(
            authType,
            email
        )
        is AuthType.OAuth -> Result.failure(exception = Throwable("Method not support!"))
    }
}