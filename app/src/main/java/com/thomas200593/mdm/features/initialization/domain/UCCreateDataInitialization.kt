package com.thomas200593.mdm.features.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.security.hashing.HashingAlgorithm
import com.thomas200593.mdm.core.design_system.security.hashing.HashingService
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UCCreateDataInitialization @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val hashingService: HashingService
) {
    operator fun invoke(dtoInitialization: DTOInitialization) {
        val hashed = hashingService.hash(string = dtoInitialization.password, algorithm = HashingAlgorithm.BCrypt)
    }
}