package com.thomas200593.mdm.features.onboarding.repository

import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.onboarding.entity.Onboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoOnboarding {
    val data : Flow<List<Onboarding>>
}

internal class RepoOnboardingImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : RepoOnboarding {
    override val data = flowOf(
        listOf(
            Onboarding(
                imageRes = R.drawable.onboard_image_1,
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_desc_1
            ),
            Onboarding(
                imageRes = R.drawable.onboard_image_2,
                title =  R.string.onboarding_title_2,
                description = R.string.onboarding_desc_2
            ),
            Onboarding(
                imageRes = R.drawable.onboard_image_3,
                title =  R.string.onboarding_title_3,
                description = R.string.onboarding_desc_3
            )
        )
    ).flowOn(ioDispatcher)
}