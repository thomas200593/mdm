package com.thomas200593.mdm.core.data.local.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.features.role.entity.BuiltInRolesSeeder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModAppDatabase {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context = context, klass = AppDatabase::class.java, name = BuildConfig.APP_LOCAL_DATABASE_FILENAME)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Executors.newSingleThreadExecutor().execute {
                    val dbInstance = Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.APP_LOCAL_DATABASE_FILENAME).build()
                    runBlocking { BuiltInRolesSeeder.patchBuiltInRolesIfMissing(dbInstance.daoRole()) }
                }
            }
        })
        .build()
}