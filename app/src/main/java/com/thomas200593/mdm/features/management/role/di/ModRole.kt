package com.thomas200593.mdm.features.management.role.di

import com.thomas200593.mdm.features.management.role.repository.RepoRole
import com.thomas200593.mdm.features.management.role.repository.RepoRoleImpl
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