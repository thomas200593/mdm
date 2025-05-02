package com.thomas200593.mdm.features.user.user_role.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.thomas200593.mdm.features.role.role.entity.RoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoUserRole {
    @Transaction
    @Query("""
        SELECT r.* 
        FROM user u
        LEFT JOIN user_role ur ON u.uid = ur.user_id
        INNER JOIN role r ON r.role_code = ur.role_code
        WHERE 1 = 1 
        AND u.uid = :userId""")
    fun getUserRoles(userId: String): Flow<List<RoleEntity>>
    @Query("""DELETE FROM user_role WHERE 1 = 1""")
    suspend fun deleteAll()
}