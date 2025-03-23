package com.thomas200593.mdm.core.ui.component.permission.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.ui.component.permission.entity.DeniedAppPermissions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface RepoPermissionHandler {
    val deniedAppPermissions: Flow<DeniedAppPermissions>
    suspend fun updateDeniedPermissions(permissions: Set<String>): Preferences
}

class RepoPermissionHandlerImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatchers: CoroutineDispatcher,
    private val dataStore: DataStorePreferences
) : RepoPermissionHandler {
    override val deniedAppPermissions: Flow<DeniedAppPermissions> = dataStore.instance.data
        .map { data ->
            DeniedAppPermissions(
                deniedPermissions = data[DataStorePreferencesKeys.dsKeyDeniedAppPermissions]
                    ?.let { json -> Json.decodeFromString(json) } ?:emptySet()) }
        .flowOn(ioDispatchers)
    override suspend fun updateDeniedPermissions(permissions: Set<String>) = withContext(ioDispatchers){
        dataStore.instance.edit {
            it[DataStorePreferencesKeys.dsKeyDeniedAppPermissions] = Json.encodeToString(permissions)
        }
    }
}