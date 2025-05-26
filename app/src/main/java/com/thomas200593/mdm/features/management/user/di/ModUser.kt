package com.thomas200593.mdm.features.management.user.di

import com.thomas200593.mdm.features.management.user.repository.RepoUser
import com.thomas200593.mdm.features.management.user.repository.RepoUserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModUser {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoUserImpl) : RepoUser
}