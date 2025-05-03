package com.thomas200593.mdm.features.user_management.user_role.di

import com.thomas200593.mdm.features.user_management.user_role.repository.RepoUserRole
import com.thomas200593.mdm.features.user_management.user_role.repository.RepoUserRoleImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModUserRole {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoUserRoleImpl) : RepoUserRole
}