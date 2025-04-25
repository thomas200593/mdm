package com.thomas200593.mdm.features.user_profile.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface RepoUserProfile
class RepoUserProfileImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : RepoUserProfile