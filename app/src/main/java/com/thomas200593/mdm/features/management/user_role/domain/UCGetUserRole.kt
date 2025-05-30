package com.thomas200593.mdm.features.management.user_role.domain

import androidx.sqlite.SQLiteException
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.management.user.repository.RepoUser
import com.thomas200593.mdm.features.management.user_role.entity.FilterOption
import com.thomas200593.mdm.features.management.user_role.entity.SortOption
import com.thomas200593.mdm.features.management.user_role.repository.RepoUserRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetUserRole @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoUser: RepoUser,
    private val repoUserRole: RepoUserRole
) {
    operator fun invoke(
        user: UserEntity, query: String = Constants.STR_EMPTY,
        sortOption : SortOption = SortOption.RoleLabelAsc, filterOption : FilterOption = FilterOption.RoleTypeAll
    ) = flow {
        val userEntity = repoUser.getOneByUid(user.uid).flowOn(ioDispatcher).first().getOrThrow()
        emitAll(repoUserRole.getUserRolesAssocByUserPaged(user = userEntity, query = query, sortOption = sortOption, filterOption = filterOption).flowOn(ioDispatcher))
    }.catch { val err = mapThrowableError(it) ; throw err}.flowOn(ioDispatcher)
    private fun mapThrowableError(t: Throwable?) : Error = when (t) {
        is SQLiteException -> Error.Database.DaoQueryError(message = t.message, cause = t.cause)
        is NoSuchElementException -> Error.Database.DaoQueryNoDataError(message = t.message, cause = t.cause)
        is IllegalArgumentException -> Error.Input.MalformedError(message = t.message, cause = t.cause)
        else -> Error.UnknownError(message = t?.message, cause = t?.cause)
    }
}