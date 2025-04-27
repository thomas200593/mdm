package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.design_system.session.entity.SessionHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao interface DaoSessionHistory {
    @Query("SELECT * FROM session_history")
    fun getAll() : Flow<List<SessionHistoryEntity>>
    @Insert(entity = SessionHistoryEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(sessions : List<SessionHistoryEntity>)
}