package com.thomas200593.mdm.features.conf.common.di

import com.thomas200593.mdm.features.conf.common.repository.RepoConfCommon
import com.thomas200593.mdm.features.conf.common.repository.RepoConfCommonImpl
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