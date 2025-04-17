package com.thomas200593.mdm.core.design_system.timber_logger.di

import com.thomas200593.mdm.core.design_system.timber_logger.FileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.TimberLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModTimberLogger {
    @Binds
    @Singleton
    abstract fun bindsModule(impl : FileLogger) : TimberLogger
}