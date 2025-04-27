package com.thomas200593.mdm.core.design_system.security.hashing.di

import com.thomas200593.mdm.core.design_system.security.hashing.HashingService
import com.thomas200593.mdm.core.design_system.security.hashing.HashingServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModHashing {
    @Binds @Singleton abstract fun bindsImplementation(impl: HashingServiceImpl) : HashingService
}