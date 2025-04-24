package com.thomas200593.mdm.features.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoUser {
    @Insert(entity = UserEntity::class, onConflict = ABORT)
    suspend fun insertUser(user : UserEntity) : Long
    @Query("SELECT * FROM user WHERE 1=1 AND uid = :uid LIMIT 1")
    fun getOneByUid(uid: String) : Flow<List<UserEntity>>
    @Query("SELECT * FROM user WHERE 1=1 AND email = :email LIMIT 1")
    fun getOneByEmail(email: String) : Flow<List<UserEntity>>
}
class DaoUserImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoUser {
    override suspend fun insertUser(user : UserEntity) : Long = withContext (ioDispatcher) { appDatabase.daoUser().insertUser(user) }
    override fun getOneByUid(uid: String) = appDatabase.daoUser().getOneByUid(uid).flowOn(ioDispatcher)
    override fun getOneByEmail(email: String) = appDatabase.daoUser().getOneByEmail(email).flowOn(ioDispatcher)
}