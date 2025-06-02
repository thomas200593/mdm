package com.thomas200593.mdm.features.introduction.initialization.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.introduction.initialization.dao.DaoInitialization
import com.thomas200593.mdm.features.introduction.initialization.entity.DTOInitializationResult
import com.thomas200593.mdm.features.introduction.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.management.user_role.entity.UserRoleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createUserLocalEmailPassword(
        user : UserEntity, profile : UserProfileEntity, auth : AuthEntity, roles : Set<UserRoleEntity>
    ) : DTOInitializationResult
    suspend fun updateFirstTimeStatus(firstTimeStatus : FirstTimeStatus) : Preferences
    suspend fun rollback(user: UserEntity): Int
}
class RepoInitializationImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val daoInitialization : DaoInitialization,
    private val dataStore : DataStorePreferences
) : RepoInitialization {
    override suspend fun createUserLocalEmailPassword(
        user : UserEntity, profile : UserProfileEntity, auth : AuthEntity, roles : Set<UserRoleEntity>
    ) = withContext (ioDispatcher) { daoInitialization.insertInitialization(user, profile, auth, roles.toList()) }
    override suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus) =
        withContext (ioDispatcher) { dataStore.instance.edit { it[DataStorePreferencesKeys.dsKeyFirstTimeStatus] = firstTimeStatus.name } }
    override suspend fun rollback(user: UserEntity) = withContext (ioDispatcher) { daoInitialization.rollback(user) }
}