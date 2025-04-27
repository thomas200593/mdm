package com.thomas200593.mdm.core.data.local.datastore.di

import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModDataStorePreferences {
    @Binds @Singleton abstract fun bindsImplementation(impl: DataStorePreferencesImpl): DataStorePreferences
}