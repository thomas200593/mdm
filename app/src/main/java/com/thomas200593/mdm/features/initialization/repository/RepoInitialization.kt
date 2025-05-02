package com.thomas200593.mdm.features.initialization.repository

import android.database.sqlite.SQLiteAbortException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.initialization.dao.DaoInitialization
import com.thomas200593.mdm.features.initialization.entity.DTOInitializationResult
import com.thomas200593.mdm.features.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.user_role.entity.UserRoleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoInitialization {
    suspend fun createUserLocalEmailPassword(user: UserEntity, profile: UserProfileEntity, auth: AuthEntity, roles: Set<UserRoleEntity>): Result<DTOInitializationResult>
    suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus): Preferences
}
class RepoInitializationImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoInitialization: DaoInitialization,
    private val dataStore: DataStorePreferences
) : RepoInitialization {
    override suspend fun createUserLocalEmailPassword(user: UserEntity, profile: UserProfileEntity, auth: AuthEntity, roles: Set<UserRoleEntity>) = withContext (ioDispatcher) {
        daoInitialization.insertInitialization(user, profile, auth, roles.toList())
            .takeIf { it.userId > 0 && it.profileId > 0 && it.authId > 0 && it.rolesIds.all { id -> id > 0 } }?. let { Result.success(it) }
            ?: run { daoInitialization.rollback(user); Result.failure(Error.Database.DaoInsertError(message = "Initialization failed for user ${user.email}, rolling back", cause = SQLiteAbortException())) }
    }
    override suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus) =
        withContext (ioDispatcher) { dataStore.instance.edit { it[DataStorePreferencesKeys.dsKeyFirstTimeStatus] = firstTimeStatus.name } }
}