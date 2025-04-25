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
        runCatching { userRole.takeIf { daoUserRole.insertAll(it.toList()).isNotEmpty() } ?: throw IllegalStateException("Cannot add user roles to database!") }
            .fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
    }
}