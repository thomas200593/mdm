package com.thomas200593.mdm.features.role.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.role.dao.DaoRole
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface RepoRole
class RepoRoleImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoRole: DaoRole
) : RepoRole