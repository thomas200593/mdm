package com.thomas200593.mdm.features.conf.__language.di

import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.__language.repository.RepoImplConfLanguage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfLanguage {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoImplConfLanguage) : RepoConfLanguage
}