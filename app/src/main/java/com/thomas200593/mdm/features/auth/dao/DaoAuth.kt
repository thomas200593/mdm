package com.thomas200593.mdm.features.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface DaoAuth {
    @Insert(entity = AuthEntity::class, onConflict = REPLACE)
    suspend fun insertAuth(authEntity: AuthEntity)
    @Query("SELECT * FROM auth WHERE userId = :userId AND authType = :authType LIMIT 1;")
    fun getAuthByUserIdAndType(userId: String, authType: AuthType) : Flow<AuthEntity?>
    @Query("DELETE FROM auth WHERE userId = :userId")
    suspend fun deleteAuthByUserId(userId: String)
}
class DaoAuthImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : DaoAuth {
    override suspend fun insertAuth(authEntity: AuthEntity) = appDatabase.daoAuth().insertAuth(authEntity)
    override fun getAuthByUserIdAndType(userId: String, authType: AuthType) = appDatabase.daoAuth().getAuthByUserIdAndType(userId, authType)
    override suspend fun deleteAuthByUserId(userId: String) = appDatabase.daoAuth().deleteAuthByUserId(userId)
}