package com.thomas200593.mdm.features.auth.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.DTOSignIn
import com.thomas200593.mdm.features.auth.repository.RepoAuth
import com.thomas200593.mdm.features.user.repository.RepoUser
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UCSignIn @Inject constructor(
    private val repoUser: RepoUser,
    private val repoAuth: RepoAuth<AuthType>
) {
    suspend operator fun invoke(dto: DTOSignIn) = when (dto.authType) {
        is AuthType.LocalEmailPassword -> runCatching {
            val user = repoUser.getOneByEmail(dto.email).first().getOrThrow()
            val auth = repoAuth.getAuthByUser(user).first().getOrThrow()
            user to auth
            /*TODO : Cross check dto vs result*/
        }.fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
    }
}