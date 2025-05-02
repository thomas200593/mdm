package com.thomas200593.mdm.features.initial.onboarding.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.initial.onboarding.entity.Onboarding
import com.thomas200593.mdm.features.initial.onboarding.entity.OnboardingStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoOnboarding {
    val list : Flow<List<Onboarding>>
    suspend fun hide(): Preferences
}
internal class RepoOnboardingImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dataStore: DataStorePreferences
) : RepoOnboarding {
    override val list = flowOf(
        listOf(
            Onboarding(
                imageRes = R.drawable.onboard_image_1,
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_desc_1
            ),
            Onboarding(
                imageRes = R.drawable.onboard_image_2,
                title = R.string.onboarding_title_2,
                description = R.string.onboarding_desc_2
            ),
            Onboarding(
                imageRes = R.drawable.onboard_image_3,
                title = R.string.onboarding_title_3,
                description = R.string.onboarding_desc_3
            )
        )
    ).flowOn(ioDispatcher)
    override suspend fun hide() = withContext(ioDispatcher) {
        dataStore.instance.edit {
            it[DataStorePreferencesKeys.dsKeyOnboardingStatus] = OnboardingStatus.HIDE.name
        }
    }
}