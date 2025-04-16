package com.thomas200593.mdm.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.coroutine_scope.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

interface DataStorePreferences {
    val instance: DataStore<Preferences>
}
class DataStorePreferencesImpl @Inject constructor(
    @ApplicationContext context: Context,
    @ApplicationScope scope: CoroutineScope,
    @Dispatcher(CoroutineDispatchers.IO) ioDispatcher: CoroutineDispatcher
) : DataStorePreferences {
    override val instance: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(BuildConfig.APP_LOCAL_DATASTORE_FILENAME) },
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher)
    )
}