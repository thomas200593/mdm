package com.thomas200593.mdm.features.introduction.initialization.di

import com.thomas200593.mdm.features.introduction.initialization.repository.RepoInitialization
import com.thomas200593.mdm.features.introduction.initialization.repository.RepoInitializationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModInitialization {
    @Binds @Singleton abstract fun bindsRepository(impl: RepoInitializationImpl): RepoInitialization
}