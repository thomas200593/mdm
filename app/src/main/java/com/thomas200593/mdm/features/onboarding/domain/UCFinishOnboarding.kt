package com.thomas200593.mdm.features.onboarding.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.onboarding.repository.RepoOnboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UCFinishOnboarding @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoOnboarding: RepoOnboarding
) { suspend operator fun invoke() = withContext(ioDispatcher) { repoOnboarding.hide() } }