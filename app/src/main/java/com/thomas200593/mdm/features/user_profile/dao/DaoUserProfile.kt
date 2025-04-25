package com.thomas200593.mdm.features.user_profile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoUserProfile {
    @Insert(entity = UserProfileEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfileEntity) : Long
}
class DaoUserProfileImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoUserProfile {
    override suspend fun insertUserProfile(userProfile: UserProfileEntity): Long = withContext (ioDispatcher) { appDatabase.daoUserProfile().insertUserProfile(userProfile) }
}