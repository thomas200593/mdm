package com.thomas200593.mdm.features.user_management.user_role.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.user_management.user.entity.UserEntity
import com.thomas200593.mdm.features.user_management.user.repository.RepoUser
import com.thomas200593.mdm.features.user_management.user_role.repository.RepoUserRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetUserRole @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoUser: RepoUser,
    private val repoUserRole: RepoUserRole
) { operator fun invoke(user: UserEntity) = flow {
    val user = repoUser.getOneByUid(user.uid).flowOn(ioDispatcher).first().getOrThrow()
    val roles = runCatching { repoUserRole.getUserRoles(user).flowOn(ioDispatcher).first().getOrThrow() }
    emit(roles)
}.flowOn(ioDispatcher) }