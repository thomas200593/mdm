package com.thomas200593.mdm.core.design_system.timber_logger.di

import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLoggerImpl
import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModTimberFileLogger {
    @Binds
    @Singleton
    abstract fun bindsModule(impl : TimberFileLoggerImpl) : TimberFileLogger
}