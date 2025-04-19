package com.thomas200593.mdm.features.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import kotlinx.coroutines.flow.Flow
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
    private val appDatabase: AppDatabase
) : DaoAuth {
    override suspend fun insertAuth(authEntity: AuthEntity) = appDatabase.daoAuth().insertAuth(authEntity)
    override fun getAuthByUserId(userId: String) = appDatabase.daoAuth().getAuthByUserId(userId)
    override suspend fun deleteAuthByUserId(userId: String) = appDatabase.daoAuth().deleteAuthByUserId(userId)
}