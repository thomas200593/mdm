package com.thomas200593.mdm.features.initial.domain

import com.thomas200593.mdm.features.conf.common.repository.RepoConfCommon
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UCGetDataInitial @Inject constructor(
    private val repoConfCommon: RepoConfCommon
) {
    operator fun invoke() = flowOf { /*TODO*/ }
}