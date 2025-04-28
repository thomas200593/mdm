package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.DTOSessionUserData
import kotlinx.coroutines.flow.Flow

@Dao interface DaoSession {
    @Query("SELECT * FROM session")
    fun getAll() : Flow<List<SessionEntity>>
    @Transaction
    @Query("SELECT * FROM session WHERE 1=1 LIMIT 1")
    fun getCurrent() : Flow<List<DTOSessionUserData>>
    @Query("DELETE FROM session")
    suspend fun deleteAll()
    @Insert(entity = SessionEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun create(session: SessionEntity)
}