package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import javax.inject.Inject

/**
 * Use case for creating an initial user during the initialization process.
 *
 * This class interacts with [RepoInitialization] to create a user based on the provided authentication type.
 *
 * @param repoInitialization The repository responsible for user initialization.
 */
class UCCreateInitialUser @Inject constructor(
    private val repoInitialization: RepoInitialization
) {
    /**
     * Creates a user based on the given [DTOInitialization] data.
     * @param dto The user data required for initialization.
     * @return A [Result] containing the created [DTOInitialization] on success, or an error on failure.
     */
    suspend operator fun invoke(dto : DTOInitialization) : Result<DTOInitialization> = when(dto.authType) {
        is AuthType.LocalEmailPassword -> {
            repoInitialization.createUserLocalEmailPassword(dto).fold(
                onSuccess = {
                    // Update the first-time status after successful user creation.
                    repoInitialization.updateFirstTimeStatus(FirstTimeStatus.NO)
                    Result.success(it)
                },
                onFailure = { Result.failure(it) }
            )
        }
    }
}