package com.thomas200593.mdm.features.user.user_role.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.role.role.entity.RoleEntity
import com.thomas200593.mdm.features.user.user.entity.UserEntity
import com.thomas200593.mdm.features.user.user_role.dao.DaoUserRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoUserRole {
    fun getUserRoles(user: UserEntity) : Flow<Result<List<RoleEntity>>>
    suspend fun deleteAll()
}
class RepoUserRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUserRole: DaoUserRole
) : RepoUserRole {
    override fun getUserRoles(user: UserEntity): Flow<Result<List<RoleEntity>>> = daoUserRole.getUserRoles(user.uid).flowOn(ioDispatcher)
        .map { list ->
            if(list.isNotEmpty()) Result.success(list)
            else Result.failure(Error.Database.DaoQueryNoDataError(message = "User ${user.email} has no roles associate with"))
        }.catch { err -> emit(Result.failure(Error.Database.DaoQueryError(message = err.message, cause = err))) }

    override suspend fun deleteAll() = withContext (ioDispatcher) { daoUserRole.deleteAll() }
}