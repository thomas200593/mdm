package com.thomas200593.mdm.features.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoAuth {
    @Insert(entity = AuthEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth(auth: AuthEntity)
    @Query("SELECT * FROM auth WHERE 1=1 AND user_id = :userId LIMIT 1")
    fun getAuthByUserId(userId : String) : Flow<List<AuthEntity>>
    @Query("DELETE FROM auth WHERE user_id = :userId")
    suspend fun deleteAuthByUserId(userId : String)
}