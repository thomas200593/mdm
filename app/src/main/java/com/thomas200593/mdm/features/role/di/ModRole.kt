package com.thomas200593.mdm.features.role.di

import com.thomas200593.mdm.features.role.dao.DaoRole
import com.thomas200593.mdm.features.role.dao.DaoRoleImpl
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
    abstract fun bindsDao(impl: DaoRoleImpl) : DaoRole
}