package com.thomas200593.mdm.features.user_role.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_role.dao.DaoUserRole
import com.thomas200593.mdm.features.user_role.entity.UserRoleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoUserRole {
    suspend fun insertAll(userRole : Set<UserRoleEntity>) : Result<Set<UserRoleEntity>>
}
class RepoUserRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoUserRole: DaoUserRole
) : RepoUserRole {
    override suspend fun insertAll(userRole: Set<UserRoleEntity>): Result<Set<UserRoleEntity>> = withContext (ioDispatcher) {
        runCatching {
            val result = daoUserRole.insertAll(userRole.toList())
            if (result.size == userRole.size && result.all { it > 0 }) userRole
            else throw IllegalStateException("One or more user roles failed to insert!")
        }.fold(onSuccess = { Result.success(it) }, onFailure = { it.printStackTrace(); Result.failure(it) })
    }
}