package com.thomas200593.mdm.features.common.cnf_common.di

import com.thomas200593.mdm.features.common.cnf_common.repository.RepoConfCommon
import com.thomas200593.mdm.features.common.cnf_common.repository.RepoConfCommonImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfCommon {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoConfCommonImpl): RepoConfCommon
}