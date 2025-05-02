package com.thomas200593.mdm.features.role.role.di

import com.thomas200593.mdm.features.role.role.repository.RepoRole
import com.thomas200593.mdm.features.role.role.repository.RepoRoleImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModRole  {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoRoleImpl) : RepoRole
}