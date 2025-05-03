package com.thomas200593.mdm.features.user_management.role.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_management.role.dao.DaoRole
import com.thomas200593.mdm.features.user_management.role.entity.RoleEntity
import com.thomas200593.mdm.features.user_management.role.entity.RoleType
import com.thomas200593.mdm.features.user_management.role.entity.TypeConverterRoleType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoRole {
    fun getBuiltInRoles() : Flow<Result<List<RoleEntity>>>
}
class RepoRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoRole: DaoRole
) : RepoRole {
    override fun getBuiltInRoles(): Flow<Result<List<RoleEntity>>> = daoRole
        .getBuiltInRoles(TypeConverterRoleType().toJson(RoleType.BuiltIn).orEmpty()).flowOn(ioDispatcher)
        .map { Result.success(it) }.catch { emit(Result.failure(it)) }
}