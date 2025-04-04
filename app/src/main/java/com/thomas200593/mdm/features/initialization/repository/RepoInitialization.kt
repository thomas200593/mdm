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
import com.thomas200593.mdm.features.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository interface for handling user initialization operations.
 */
interface RepoInitialization {
    /**
     * Creates a new user using local email and password authentication.
     *
     * @param dto The user initialization data.
     * @return A [Result] containing the created [DTOInitialization] on success, or an error on failure.
     */
    suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization>
    /**
     * Updates the first-time user status in the data store.
     *
     * @param firstTimeStatus The new [FirstTimeStatus] value.
     * @return A [Preferences] object after updating the data store.
     */
    suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus): Preferences
}
/**
 * Implementation of [RepoInitialization], handling user creation and first-time status updates.
 *
 * This class interacts with [RepoUser] and [RepoAuth] to manage user-related operations
 * and stores first-time user status in [DataStorePreferences].
 *
 * @param ioDispatcher The [CoroutineDispatcher] for executing database operations on the IO thread.
 * @param repoUser The repository responsible for user-related operations.
 * @param repoAuth The authentication repository for handling user authentication.
 * @param dataStore The DataStore instance for persisting user preferences.
 */
class RepoInitializationImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoUser: RepoUser,
    private val repoAuth: RepoAuth<AuthType>,
    private val dataStore: DataStorePreferences
) : RepoInitialization {
    /**
     * Creates a new user using local email and password authentication.
     *
     * This function first attempts to create or retrieve an existing user entity.
     * If successful, it then registers authentication credentials for the user.
     *
     * @param dto The user initialization data.
     * @return A [Result] containing the created [DTOInitialization] on success, or an error on failure.
     */
    @Transaction
    override suspend fun createUserLocalEmailPassword(dto: DTOInitialization): Result<DTOInitialization> =
        repoUser.getOrCreateUser(dto.toUserEntity(UUIDv7.generateAsString())).fold(
            onSuccess = { user ->
                repoAuth.registerAuthLocalEmailPassword(dto.toAuthEntity(user.uid)).fold(
                    onSuccess = { Result.success(dto) },
                    onFailure = { Result.failure(it) }
                )
            },
            onFailure = { Result.failure(it) }
        )
    /**
     * Updates the first-time user status in the data store.
     *
     * This function runs on the IO dispatcher to ensure efficient background execution.
     *
     * @param firstTimeStatus The new [FirstTimeStatus] value.
     * @return A [Preferences] object after updating the data store.
     */
    override suspend fun updateFirstTimeStatus(firstTimeStatus: FirstTimeStatus) =
        withContext(ioDispatcher){ dataStore.instance.edit { it[DataStorePreferencesKeys.dsKeyFirstTimeStatus] = firstTimeStatus.name } }
}