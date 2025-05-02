package com.thomas200593.mdm.features.initial._onboarding.di

import com.thomas200593.mdm.features.initial._onboarding.repository.RepoOnboarding
import com.thomas200593.mdm.features.initial._onboarding.repository.RepoOnboardingImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModOnboarding {
    @Binds
    @Singleton
    internal abstract fun bindsRepository(impl: RepoOnboardingImpl): RepoOnboarding
}