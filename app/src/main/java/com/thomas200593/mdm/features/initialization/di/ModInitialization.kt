package com.thomas200593.mdm.features.initialization.di

import com.thomas200593.mdm.features.initialization.repository.RepoInitialization
import com.thomas200593.mdm.features.initialization.repository.RepoInitializationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing dependencies related to initialization.
 *
 * This module binds the [RepoInitializationImpl] implementation to the [RepoInitialization] interface.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ModInitialization {
    /**
     * Binds [RepoInitializationImpl] to [RepoInitialization] as a singleton.
     *
     * @param impl The concrete implementation of [RepoInitialization].
     * @return The bound instance of [RepoInitialization].
     */
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoInitializationImpl): RepoInitialization
}