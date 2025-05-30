package com.thomas200593.mdm.features.management.user_role.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.RoleType
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.management.user_role.dao.DaoUserRole
import com.thomas200593.mdm.features.management.user_role.entity.FilterOption
import com.thomas200593.mdm.features.management.user_role.entity.SortOption
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoUserRole {
    suspend fun deleteAll()
    fun getUserRolesCountByUser(user : UserEntity) : Flow<Long>
    fun getUserRolesAssocByUserPaged(
        user: UserEntity,
        query: String,
        sortOption: SortOption,
        filterOption: FilterOption
    ): Flow<PagingData<RoleEntity>>
    fun getUserRolesAssocByUserList(
        user : UserEntity,
        query : String,
        sortOption : SortOption,
        filterOption : FilterOption
    ): Flow<List<RoleEntity>>
}
class RepoUserRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUserRole: DaoUserRole
) : RepoUserRole {
    override suspend fun deleteAll() = daoUserRole.deleteAll()
    override fun getUserRolesCountByUser(user : UserEntity) : Flow<Long> =
        daoUserRole.getUserRolesCountByUser(userId = user.uid).flowOn(ioDispatcher)
    override fun getUserRolesAssocByUserPaged(
        user: UserEntity,
        query: String,
        sortOption: SortOption,
        filterOption: FilterOption
    ): Flow<PagingData<RoleEntity>> {
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        val pagingSourceFactory = { daoUserRole.getUserRolesAssocByUserPaging(buildGetUserRolesAssocByUserQuery(
            userId = user.uid,
            query = query,
            sortOption = sortOption,
            filterOption = filterOption
        )) }
        return Pager(config = pagingConfig, pagingSourceFactory = pagingSourceFactory).flow.flowOn(ioDispatcher)
    }
    override fun getUserRolesAssocByUserList(
        user: UserEntity,
        query: String,
        sortOption: SortOption,
        filterOption: FilterOption
    ): Flow<List<RoleEntity>> {
        val sql = buildGetUserRolesAssocByUserQuery(
            userId = user.uid,
            query = query,
            sortOption = sortOption,
            filterOption = filterOption
        )
        return daoUserRole.getUserRolesAssocByUserList(sql).flowOn(ioDispatcher)
    }
    private fun buildGetUserRolesAssocByUserQuery(
        userId: String,
        query: String,
        sortOption: SortOption,
        filterOption: FilterOption
    ) : SimpleSQLiteQuery {
        val args = mutableListOf<Any>()
        val sql = buildString {
            append("""SELECT r.* FROM user u 
                LEFT JOIN user_role ur ON u.uid = ur.user_id 
                INNER JOIN role r ON r.role_code = ur.role_code 
                WHERE 1 = 1 
                AND u.uid = ?""".trimIndent())
            args.add(userId)
            filterOption.roleType?.let {
                val roleTypeString = when (it) {
                    RoleType.BuiltIn -> RoleType.BuiltIn::class.simpleName.toString()
                    RoleType.UserDefined -> RoleType.UserDefined::class.simpleName.toString()
                }
                append(" AND r.role_type = ?")
                args.add(roleTypeString)
            }
            if (query.isNotBlank()) {
                append(" AND (")
                append("LOWER(r.role_label) LIKE ? OR LOWER(r.role_code) LIKE ? OR LOWER(r.role_type) LIKE ?")
                append(")")
                repeat(3) { args.add("%$query%") }
            }
            append(" ORDER BY ")
            append(
                when (sortOption) {
                    SortOption.RoleLabelAsc -> "r.role_label ASC"
                    SortOption.RoleLabelDesc -> "r.role_label DESC"
                    SortOption.RoleTypeAsc -> "r.role_type ASC"
                    SortOption.RoleTypeDesc -> "r.role_type DESC"
                    SortOption.RoleCodeAsc -> "r.role_code ASC"
                    SortOption.RoleCodeDesc -> "r.role_code DESC"
                    /*SortOption.RoleCreatedOldest -> ""
                    SortOption.RoleCreatedNewest -> ""*/
                }
            )
        }
        return SimpleSQLiteQuery(sql, args.toTypedArray())
    }
}