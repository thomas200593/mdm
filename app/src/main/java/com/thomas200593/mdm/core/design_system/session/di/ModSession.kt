package com.thomas200593.mdm.core.design_system.session.di

import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.core.design_system.session.repository.RepoSessionImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModSession {
    @Binds @Singleton internal abstract fun bindsRepository(impl : RepoSessionImpl) : RepoSession
}