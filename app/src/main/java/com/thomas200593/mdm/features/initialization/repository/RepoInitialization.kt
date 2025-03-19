package com.thomas200593.mdm.features.initialization.repository

import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createInitialUser(dto: DTOInitialization)
}

class RepoInitializationImpl @Inject constructor() : RepoInitialization {
    override suspend fun createInitialUser(dto: DTOInitialization) {

    }
}