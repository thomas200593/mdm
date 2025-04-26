package com.thomas200593.mdm.features.user_profile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity

@Dao
interface DaoUserProfile {
    @Insert(entity = UserProfileEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfileEntity) : Long
}