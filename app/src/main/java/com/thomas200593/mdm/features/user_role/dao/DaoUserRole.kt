package com.thomas200593.mdm.features.user_role.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_role.entity.UserRoleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Dao
interface DaoUserRole {
    @Insert(entity = UserRoleEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(userRole : List<UserRoleEntity>) : List<Long>
}
class DaoUserRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoUserRole {
    override suspend fun insertAll(userRole : List<UserRoleEntity>) = withContext (ioDispatcher) { appDatabase.daoUserRole().insertAll(userRole) }
}