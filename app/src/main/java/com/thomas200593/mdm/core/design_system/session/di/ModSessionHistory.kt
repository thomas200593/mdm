package com.thomas200593.mdm.core.design_system.session.di

import com.thomas200593.mdm.core.design_system.session.dao.DaoSessionHistory
import com.thomas200593.mdm.core.design_system.session.dao.DaoSessionHistoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModSessionHistory {
    @Binds
    @Singleton
    internal abstract fun bindsDao(impl : DaoSessionHistoryImpl) : DaoSessionHistory
}