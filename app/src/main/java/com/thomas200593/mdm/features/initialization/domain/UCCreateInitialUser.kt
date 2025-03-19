package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import javax.inject.Inject

class UCCreateInitialUser @Inject constructor(
    private val repoInitialization: RepoInitialization
) {
    suspend operator fun invoke(dtoInitialization: DTOInitialization) =
        repoInitialization.createInitialUser(dtoInitialization)
}