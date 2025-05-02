package com.thomas200593.mdm.features.cnf_localization_language.di

import com.thomas200593.mdm.features.cnf_localization_language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.cnf_localization_language.repository.RepoImplConfLanguage
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