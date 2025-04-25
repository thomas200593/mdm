package com.thomas200593.mdm.features.user_profile.di

import com.thomas200593.mdm.features.user_profile.dao.DaoUserProfile
import com.thomas200593.mdm.features.user_profile.dao.DaoUserProfileImpl
import com.thomas200593.mdm.features.user_profile.repository.RepoUserProfile
import com.thomas200593.mdm.features.user_profile.repository.RepoUserProfileImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModUserProfile {
    @Binds
    @Singleton
    abstract fun bindsRepository(impl: RepoUserProfileImpl) : RepoUserProfile
    @Binds
    @Singleton
    abstract fun bindsDao(impl: DaoUserProfileImpl) : DaoUserProfile
}