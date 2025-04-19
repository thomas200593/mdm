package com.thomas200593.mdm.core.design_system.session.di

import com.thomas200593.mdm.core.design_system.session.SessionManager
import com.thomas200593.mdm.core.design_system.session.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModSessionManager {
    @Binds
    @Singleton
    internal abstract fun bindsImplementation(impl : SessionManagerImpl) : SessionManager
}