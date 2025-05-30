package com.thomas200593.mdm.features.role_selection.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.features.management.user.repository.RepoUser
import com.thomas200593.mdm.features.management.user_role.entity.FilterOption
import com.thomas200593.mdm.features.management.user_role.entity.SortOption
import com.thomas200593.mdm.features.management.user_role.repository.RepoUserRole
import com.thomas200593.mdm.features.role_selection.entity.DTORoleSelection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCSetUserRole @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repoUser : RepoUser,
    private val repoUserRole : RepoUserRole,
    private val repoSession : RepoSession
) {
    suspend operator fun invoke(dto: DTORoleSelection): Result<DTORoleSelection> {
        repoUser.getOneByEmail(dto.user.email).first().getOrThrow()
        runCatching {
            val list = repoUserRole.getUserRolesAssocByUserList(dto.user, Constants.STR_EMPTY, SortOption.RoleLabelAsc, FilterOption.RoleTypeAll)
                .flowOn(ioDispatcher).first()
            if(!list.contains(dto.role)) return Result.failure(Error.Data.NotFoundError(message = "User ${dto.user.email} has no role ${dto.role.label}"))
        }.getOrElse { throw Error.Database.DaoQueryError(message = it.message, cause = it.cause) }
        repoSession.isValid(dto.session).getOrElse { throw Error.Data.ValidationError(message = "User session ${dto.user.email} is expired / invalid") }
        //update session role, else cleanup, then throw
        val updateResult = repoSession.update(dto.session.copy(currentRoleCode = dto.role.roleCode)).getOrThrow()
        return if (updateResult > 0) Result.success(dto)
        else Result.failure(Error.Database.DaoUpdateError(message = "Failure during update role '${dto.role.label}' for user ${dto.user.email}"))
    }
}