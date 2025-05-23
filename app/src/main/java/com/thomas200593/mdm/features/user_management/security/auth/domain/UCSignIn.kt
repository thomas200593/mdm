package com.thomas200593.mdm.features.user_management.security.auth.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.security.hashing.BCrypt
import com.thomas200593.mdm.features.user_management.security.auth.entity.AuthType
import com.thomas200593.mdm.features.user_management.security.auth.entity.DTOSignIn
import com.thomas200593.mdm.features.user_management.security.auth.repository.RepoAuth
import com.thomas200593.mdm.features.user_management.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCSignIn @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val bCrypt: BCrypt,
    private val repoUser: RepoUser,
    private val repoAuth: RepoAuth<AuthType>
) {
    /*TODO*/
    suspend operator fun invoke(dto: DTOSignIn) = when (dto.authType) {
        is AuthType.LocalEmailPassword -> runCatching {
            val user = repoUser.getOneByEmail(dto.email).flowOn(ioDispatcher).first().getOrThrow()
            val auth = repoAuth.getAuthByUser(user).flowOn(ioDispatcher).first().getOrThrow()
            val result = user to auth
            (result.second.authType as? AuthType.LocalEmailPassword)?.let { storedAuth ->
                if (dto.authType.provider != storedAuth.provider) { throw IllegalArgumentException("Auth Provider mismatch!") }
                if ( ! bCrypt.verify(dto.authType.password, storedAuth.password) ) throw IllegalArgumentException("Invalid Password")
                result
            } ?: throw IllegalStateException("Auth Type Mismatch!")
        }.fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
    }
}