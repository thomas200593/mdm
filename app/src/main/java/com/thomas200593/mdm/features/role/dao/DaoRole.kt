package com.thomas200593.mdm.features.role.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.role.entity.RoleEntity
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@Dao
interface DaoRole {
    @Query("SELECT * FROM role WHERE role_type = :builtIn")
    fun getBuiltInRoles(builtIn: String?): Flow<List<RoleEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun seedsBuiltInRoles(roles: List<RoleEntity>)
}
class DaoRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoRole {
    override fun getBuiltInRoles(builtIn: String?): Flow<List<RoleEntity>> = appDatabase.daoRole().getBuiltInRoles(builtIn).flowOn(ioDispatcher)
    override suspend fun seedsBuiltInRoles(roles: List<RoleEntity>) = withContext (ioDispatcher) { appDatabase.daoRole().seedsBuiltInRoles(roles) }
}