package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.entity.SessionHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoSessionHistory {
    @Query("SELECT * FROM session_history")
    fun getAll() : Flow<List<SessionHistoryEntity>>
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(sessions : List<SessionHistoryEntity>)
}
class DaoSessionHistoryImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoSessionHistory {
    override fun getAll(): Flow<List<SessionHistoryEntity>> = appDatabase.daoSessionHistory().getAll().flowOn(ioDispatcher)
    override suspend fun insertAll(sessions: List<SessionHistoryEntity>) = withContext (ioDispatcher) { appDatabase.daoSessionHistory().insertAll(sessions) }
}