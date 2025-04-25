package com.thomas200593.mdm.features.initialization.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.room.Transaction
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.UUIDv7
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.repository.RepoAuth
import com.thomas200593.mdm.features.initialization.entity.DTOInitialization
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.initialization.entity.toAuthEntity
import com.thomas200593.mdm.features.initialization.entity.toUserEntity
import com.thomas200593.mdm.features.initialization.entity.toUserProfileEntity
import com.thomas200593.mdm.features.initialization.entity.toUserRoleEntity
import com.thomas200593.mdm.features.user.repository.RepoUser
import com.thomas200593.mdm.features.user_profile.repository.RepoUserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization>
    suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus): Preferences
}
class RepoInitializationImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoUser: RepoUser,
    private val repoUserProfile: RepoUserProfile,
    private val repoAuth: RepoAuth<AuthType>,
    private val dataStore: DataStorePreferences
) : RepoInitialization {
    @Transaction
    override suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization> = withContext (ioDispatcher) {
        val result = repoUser.getOrCreateUser(dto.toUserEntity(UUIDv7.generateAsString()))
            .fold(
                onSuccess = { user ->
                    val userProfile = repoUserProfile.insertUserProfile(dto.toUserProfileEntity(user.uid))
                    val auth = repoAuth.registerAuthLocalEmailPassword(dto.toAuthEntity(user.uid))
                    val setOfUserRoles = dto.toUserRoleEntity(user.uid, dto.initialSetOfRoles)
                    if (userProfile.isSuccess && auth.isSuccess) Result.success(dto)
                    else Result.failure(userProfile.exceptionOrNull() ?: auth.exceptionOrNull() ?: IllegalStateException("Error creating subsequent data"))
                },
                onFailure = { Result.failure(it) }
            )
        result
    }
    override suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus) =
        withContext (ioDispatcher) { dataStore.instance.edit { it[DataStorePreferencesKeys.dsKeyFirstTimeStatus] = firstTimeStatus.name } }
}