package com.thomas200593.mdm.core.design_system.network_monitor.di

import com.thomas200593.mdm.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mdm.core.design_system.network_monitor.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModNetworkMonitor {
    @Binds
    @Singleton
    internal abstract fun bindsImplementation(impl: NetworkMonitorImpl): NetworkMonitor
}