package com.thomas200593.mdm.features.user_management.security.session.di

import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSessionHistory
import com.thomas200593.mdm.features.user_management.security.session.repository.RepoSessionHistoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModSessionHistory {
    @Binds @Singleton internal abstract fun bindsRepository(impl: RepoSessionHistoryImpl): RepoSessionHistory
}