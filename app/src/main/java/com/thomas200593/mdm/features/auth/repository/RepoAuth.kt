package com.thomas200593.mdm.features.auth.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoAuth<T: AuthType> {
    fun getAuthByUser(user: UserEntity): Flow<Result<AuthEntity>>
}
class RepoAuthImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val daoAuth: DaoAuth
) : RepoAuth<AuthType> {
    override fun getAuthByUser(user : UserEntity) = daoAuth.getAuthByUserId(userId = user.uid)
        .flowOn(ioDispatcher)
        .map { it.firstOrNull()
            ?. let { Result.success(it) }
            ?: Result.failure(Error.Database.DaoQueryNoDataError(message = "Auth for user ${user.email} not found!"))
        }
        .catch { e -> emit(Result.failure(Error.Database.DaoQueryError(cause = e, message = e.message))) }
}