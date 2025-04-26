package com.thomas200593.mdm.features.user_role.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.thomas200593.mdm.features.user_role.entity.UserRoleEntity

@Dao
interface DaoUserRole {
    @Insert(entity = UserRoleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userRole : List<UserRoleEntity>) : List<Long>
}