package com.thomas200593.mdm.core.design_system.session.dao

import androidx.room.Dao
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoSession {
    @Query("SELECT * FROM session LIMIT 1")
    fun getCurrentSession() : Flow<SessionEntity?>
    @Query("DELETE FROM session")
    suspend fun delete()
}
class DaoSessionImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoSession {
    override fun getCurrentSession() = appDatabase.daoSession().getCurrentSession().flowOn(ioDispatcher)
    override suspend fun delete() = withContext (ioDispatcher) { appDatabase.daoSession().delete() }
}