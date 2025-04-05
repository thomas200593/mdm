package com.thomas200593.mdm.features.auth.di

import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.dao.DaoAuthImpl
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.repository.RepoAuth
import com.thomas200593.mdm.features.auth.repository.RepoAuthImpl
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
    @Binds
    @Singleton
    abstract fun bindsDao(impl: DaoAuthImpl) : DaoAuth
}