package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoSession {
    @Query("SELECT * FROM session")
    fun getAll() : Flow<List<SessionEntity>>
    @Query("SELECT * FROM session LIMIT 1")
    fun getCurrentSession() : Flow<List<SessionEntity>>
    @Query("DELETE FROM session")
    suspend fun deleteAll()
    /*TODO: move to separate class*/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAllSessionHistory(sessions: List<SessionHistoryEntity>)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(session: SessionEntity)
}
class DaoSessionImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoSession {
    override fun getAll(): Flow<List<SessionEntity>> = appDatabase.daoSession().getAll().flowOn(ioDispatcher)
    override fun getCurrentSession() = appDatabase.daoSession().getCurrentSession().flowOn(ioDispatcher)
    override suspend fun deleteAll() = withContext (ioDispatcher) { appDatabase.daoSession().deleteAll() }
    override suspend fun insertAllSessionHistory(sessions: List<SessionHistoryEntity>) = withContext (ioDispatcher) { appDatabase.daoSession().insertAllSessionHistory(sessions) }
    override suspend fun create(session: SessionEntity) = withContext (ioDispatcher) { appDatabase.daoSession().create(session) }
}