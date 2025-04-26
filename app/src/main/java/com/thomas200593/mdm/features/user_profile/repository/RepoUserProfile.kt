package com.thomas200593.mdm.features.user_profile.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_profile.dao.DaoUserProfile
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoUserProfile {
    suspend fun insertUserProfile(userProfile: UserProfileEntity) : Result<UserProfileEntity>
}
class RepoUserProfileImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUserProfile: DaoUserProfile
) : RepoUserProfile {
    override suspend fun insertUserProfile(userProfile: UserProfileEntity): Result<UserProfileEntity> = withContext (ioDispatcher) {
        runCatching { userProfile.takeIf { daoUserProfile.insertUserProfile(it) > 0 } ?: throw IllegalStateException("Cannot create User Profile to database!") }
            .fold(onSuccess = { Result.success(it) }, onFailure = { it.printStackTrace(); Result.failure(it) })
    }
}