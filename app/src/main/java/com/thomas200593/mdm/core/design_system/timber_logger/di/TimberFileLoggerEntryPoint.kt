package com.thomas200593.mdm.core.design_system.timber_logger.di

import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint @InstallIn(SingletonComponent::class) interface TimberFileLoggerEntryPoint {
    fun timberFileLogger() : TimberFileLogger
}