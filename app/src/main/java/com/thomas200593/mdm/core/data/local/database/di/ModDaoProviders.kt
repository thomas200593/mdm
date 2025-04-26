package com.thomas200593.mdm.core.data.local.database.di

import com.thomas200593.mdm.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object ModDaoProviders {
    @Provides @Singleton fun daoSession(db : AppDatabase) = db.daoSession()
    @Provides @Singleton fun daoSessionHistory(db : AppDatabase) = db.daoSessionHistory()
    @Provides @Singleton fun daoUser(db : AppDatabase) = db.daoUser()
    @Provides @Singleton fun daoUserProfile(db : AppDatabase) = db.daoUserProfile()
    @Provides @Singleton fun daoUserRole(db : AppDatabase) = db.daoUserRole()
    @Provides @Singleton fun daoRole(db : AppDatabase) = db.daoRole()
    @Provides @Singleton fun daoAuth(db : AppDatabase) = db.daoAuth()
}