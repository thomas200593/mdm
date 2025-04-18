package com.thomas200593.mdm.features.auth.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.DTOSignIn
import com.thomas200593.mdm.features.user.repository.RepoUser
import javax.inject.Inject

class UCSignIn @Inject constructor(
    private val repoUser: RepoUser
) {
    suspend operator fun invoke(dto: DTOSignIn) = when (dto.authType) {
        is AuthType.LocalEmailPassword -> { val result = repoUser.getOneByEmail(dto.email) }
    }
}