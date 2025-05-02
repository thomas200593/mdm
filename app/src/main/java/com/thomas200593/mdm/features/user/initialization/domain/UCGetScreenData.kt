package com.thomas200593.mdm.features.user.initialization.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.common.domain.UCGetConfCommonCurrent
import com.thomas200593.mdm.features.role.role.entity.BuiltInRolesSeeder
import com.thomas200593.mdm.features.role.role.repository.RepoRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetScreenData @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val ucGetConfCommonCurrent: UCGetConfCommonCurrent,
    private val repoRole: RepoRole
) {
    operator fun invoke() = combine(
        flow = ucGetConfCommonCurrent.invoke().flowOn(ioDispatcher),
        flow2 = repoRole.getBuiltInRoles().flowOn(ioDispatcher)
    ) { confCommon, setOfRoles -> confCommon to setOfRoles.getOrDefault(emptyList())
        .filter { it.roleCode in setOf(BuiltInRolesSeeder.SYSTEM_OWNER, BuiltInRolesSeeder.SYSTEM_ADMIN) }.toSet()
    }.flowOn(ioDispatcher)
}