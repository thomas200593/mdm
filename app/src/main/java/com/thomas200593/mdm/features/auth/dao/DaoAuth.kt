package com.thomas200593.mdm.features.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
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
    @Insert(entity = AuthEntity::class, onConflict = REPLACE)
    suspend fun insertAuth(authEntity: AuthEntity)
    @Query("SELECT * FROM auth WHERE userId = :userId LIMIT 1;")
    fun getAuthByUserId(userId: String) : Flow<AuthEntity?>
    @Query("DELETE FROM auth WHERE userId = :userId")
    suspend fun deleteAuthByUserId(userId: String)
}
class DaoAuthImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoAuth {
    override suspend fun insertAuth(authEntity: AuthEntity) = withContext (ioDispatcher) { appDatabase.daoAuth().insertAuth(authEntity) }
    override fun getAuthByUserId(userId: String) = appDatabase.daoAuth().getAuthByUserId(userId).flowOn(ioDispatcher)
    override suspend fun deleteAuthByUserId(userId: String) = withContext (ioDispatcher) { appDatabase.daoAuth().deleteAuthByUserId(userId) }
}