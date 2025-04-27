package com.thomas200593.mdm.core.design_system.coroutine_dispatchers.di

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module @InstallIn(SingletonComponent::class) object ModDispatcher {
    @Provides @Dispatcher(CoroutineDispatchers.IO) fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
    @Provides @Dispatcher(CoroutineDispatchers.Default) fun providesDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
    @Provides @Dispatcher(CoroutineDispatchers.Main) fun providesDispatcherMain(): CoroutineDispatcher = Dispatchers.Main
}