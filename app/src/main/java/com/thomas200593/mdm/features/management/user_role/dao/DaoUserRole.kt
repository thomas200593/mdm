package com.thomas200593.mdm.features.management.user_role.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import kotlinx.coroutines.flow.Flow

@Dao interface DaoUserRole {
    @Transaction @Query("""
        SELECT r.* 
        FROM user u
        LEFT JOIN user_role ur ON u.uid = ur.user_id
        INNER JOIN role r ON r.role_code = ur.role_code
        WHERE 1 = 1 
        AND u.uid = :userId""")
    fun getUserRoles(userId: String): Flow<List<RoleEntity>>
    @Transaction @Query("""
        SELECT r.* 
        FROM user u
        LEFT JOIN user_role ur ON u.uid = ur.user_id
        INNER JOIN role r ON r.role_code = ur.role_code
        WHERE 1 = 1 
        AND u.uid = :userId
    """)
    fun getUserRolesPaged(userId: String) : PagingSource<Int, RoleEntity>
    @Transaction @RawQuery(observedEntities = [RoleEntity::class])
    fun getUserRolesAssocByUser(query: SupportSQLiteQuery): PagingSource<Int, RoleEntity>
    @Query("""DELETE FROM user_role WHERE 1 = 1""")
    suspend fun deleteAll()
    @Transaction @Query("""
        SELECT COUNT(ur.user_id) FROM user_role ur WHERE 1 = 1
        AND ur.user_id = :userId
    """)
    fun getUserRolesCountByUser(userId: String) : Flow<Long>
}