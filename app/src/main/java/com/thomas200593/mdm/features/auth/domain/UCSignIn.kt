package com.thomas200593.mdm.features.auth.domain

import com.thomas200593.mdm.features.auth.entity.AuthType
import com.thomas200593.mdm.features.auth.entity.DTOSignIn
import javax.inject.Inject

class UCSignIn @Inject constructor(
) {
    suspend operator fun invoke(dto: DTOSignIn) = when (dto.authType) {
        is AuthType.LocalEmailPassword -> {  }
    }
}