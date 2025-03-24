package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    private val repoInitialization: RepoInitialization
) {
    suspend operator fun invoke(dto : DTOInitialization) : Result<DTOInitialization> = when(dto.authType) {
        is AuthType.LocalEmailPassword -> {
            repoInitialization.createUserLocalEmailPassword(dto).fold(
                onSuccess = {
                    //repoInitialization.updateFirstTimeStatus(FirstTimeStatus.NO)
                    Result.success(it)
                },
                onFailure = { Result.failure(it) }
            )
        }
    }
}