package com.thomas200593.mdm.features.user_management.security.auth.di

import com.thomas200593.mdm.features.user_management.security.auth.entity.AuthType
import com.thomas200593.mdm.features.user_management.security.auth.repository.RepoAuth
import com.thomas200593.mdm.features.user_management.security.auth.repository.RepoAuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModAuth {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoAuthImpl) : RepoAuth<AuthType>
}