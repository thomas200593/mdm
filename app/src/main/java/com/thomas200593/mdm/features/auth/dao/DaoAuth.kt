package com.thomas200593.mdm.features.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoAuth {
    @Insert(entity = AuthEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth(auth: AuthEntity)
    @Query("SELECT * FROM auth WHERE 1=1 AND user_id = :userId LIMIT 1")
    fun getAuthByUserId(userId : String) : Flow<List<AuthEntity>>
    @Query("DELETE FROM auth WHERE user_id = :userId")
    suspend fun deleteAuthByUserId(userId : String)
}
class DaoAuthImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoAuth {
    override suspend fun insertAuth(auth: AuthEntity) = withContext (ioDispatcher) { appDatabase.daoAuth().insertAuth(auth) }
    override fun getAuthByUserId(userId: String) = appDatabase.daoAuth().getAuthByUserId(userId).flowOn(ioDispatcher)
    override suspend fun deleteAuthByUserId(userId: String) = withContext (ioDispatcher) { appDatabase.daoAuth().deleteAuthByUserId(userId) }
}