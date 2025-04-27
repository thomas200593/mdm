package com.thomas200593.mdm.core.ui.component.permission.di

import com.thomas200593.mdm.core.ui.component.permission.repository.RepoPermissionHandler
import com.thomas200593.mdm.core.ui.component.permission.repository.RepoPermissionHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) abstract class ModPermissionHandler {
    @Binds @Singleton abstract fun bindsRepository(impl: RepoPermissionHandlerImpl) : RepoPermissionHandler
}