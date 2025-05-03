package com.thomas200593.mdm.features.user_management.role.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoRole {
    @Query("SELECT * FROM role WHERE role_type = :builtIn")
    fun getBuiltInRoles(builtIn: String?): Flow<List<RoleEntity>>
    @Insert(entity = RoleEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun seedsBuiltInRoles(roles: List<RoleEntity>)
}