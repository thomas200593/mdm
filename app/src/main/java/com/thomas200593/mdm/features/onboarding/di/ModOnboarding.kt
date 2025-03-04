package com.thomas200593.mdm.features.onboarding.di

import com.thomas200593.mdm.features.onboarding.repository.RepoOnboarding
import com.thomas200593.mdm.features.onboarding.repository.RepoOnboardingImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModOnboarding {
    @Binds
    internal abstract fun bindsRepository(impl: RepoOnboardingImpl): RepoOnboarding
}