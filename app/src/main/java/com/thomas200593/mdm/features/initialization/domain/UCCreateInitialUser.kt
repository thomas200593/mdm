package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    private val repoInitialization: RepoInitialization
) {
    suspend operator fun invoke(dtoInitialization: DTOInitialization) = when(dtoInitialization.authType) {
        is AuthType.LocalEmailPassword -> {}
        is AuthType.OAuth -> Result.failure<Throwable>(Throwable("Method not Available"))
    }
}