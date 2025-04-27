package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao interface DaoSession {
    @Query("SELECT * FROM session")
    fun getAll() : Flow<List<SessionEntity>>
    @Query("SELECT * FROM session LIMIT 1")
    fun getCurrentSession() : Flow<List<SessionEntity>>
    @Query("DELETE FROM session")
    suspend fun deleteAll()
    @Insert(entity = SessionEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun create(session: SessionEntity)
}