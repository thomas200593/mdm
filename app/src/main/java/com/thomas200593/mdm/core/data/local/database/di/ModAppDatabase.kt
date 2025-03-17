package com.thomas200593.mdm.core.data.local.database.di

import android.content.Context
import androidx.room.Room
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModAppDatabase {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = BuildConfig.APP_LOCAL_DATABASE_FILENAME
    ).build()
}