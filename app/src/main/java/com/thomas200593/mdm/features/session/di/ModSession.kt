package com.thomas200593.mdm.features.session.di

import com.thomas200593.mdm.features.session.repository.RepoSession
import com.thomas200593.mdm.features.session.repository.RepoSessionImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModSession {
    @Binds @Singleton internal abstract fun bindsRepository(impl : RepoSessionImpl) : RepoSession
}