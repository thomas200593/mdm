package com.thomas200593.mdm.features.common.cnf_common.repository

import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.Constants.STR_EMPTY
import com.thomas200593.mdm.features.common.cnf_common.entity.Common
import com.thomas200593.mdm.features.common.cnf_ui_contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.common.cnf_localization_country.entity.Country
import com.thomas200593.mdm.features.common.cnf_ui_dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.common.cnf_ui_font_size.entity.FontSize
import com.thomas200593.mdm.features.common.cnf_localization_language.entity.Language
import com.thomas200593.mdm.features.common.cnf_ui_theme.entity.Theme
import com.thomas200593.mdm.features.common.cnf_localization.entity.Localization
import com.thomas200593.mdm.features.common.cnf_ui.entity.UI
import com.thomas200593.mdm.features.introduction.initialization.entity.FirstTimeStatus
import com.thomas200593.mdm.features.introduction.onboarding.entity.OnboardingStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

interface RepoConfCommon {
    val confCommon: Flow<Common>
}

class RepoConfCommonImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    dataStore: DataStorePreferences
) : RepoConfCommon {
    override val confCommon: Flow<Common> = dataStore.instance.data.map { data ->
        Common(
            ui = UI(
                theme = data[DataStorePreferencesKeys.dsKeyTheme]?.let { Theme.valueOf(it) }
                    ?: Theme.Companion.defaultValue,
                contrastAccent = data[DataStorePreferencesKeys.dsKeyContrastAccent]?.let {
                    ContrastAccent.valueOf(
                        it
                    )
                } ?: ContrastAccent.Companion.defaultValue,
                dynamicColor = data[DataStorePreferencesKeys.dsKeyDynamicColor]?.let {
                    DynamicColor.valueOf(
                        it
                    )
                } ?: DynamicColor.Companion.defaultValue,
                fontSize = data[DataStorePreferencesKeys.dsKeyFontSize]?.let { FontSize.valueOf(it) }
                    ?: FontSize.Companion.defaultValue
            ),
            localization = Localization(
                language = data[DataStorePreferencesKeys.dsKeyLanguage]?.let { Language.valueOf(it) }
                    ?: Language.Companion.defaultValue,
                country = data[DataStorePreferencesKeys.dsKeyCountry]?.let {
                    Country(
                        iso2 = it,
                        iso3 = Locale(STR_EMPTY, it).isO3Country,
                        name = Locale(STR_EMPTY, it).displayName,
                        flag = Country.Companion.getFlagByISOCode(it)
                    )
                } ?: Country.Companion.defaultValue
            ),
            onboardingStatus = data[DataStorePreferencesKeys.dsKeyOnboardingStatus]?.let {
                OnboardingStatus.valueOf(
                    it
                )
            } ?: OnboardingStatus.defaultValue,
            firstTimeStatus = data[DataStorePreferencesKeys.dsKeyFirstTimeStatus]?.let {
                FirstTimeStatus.valueOf(
                    it
                )
            } ?: FirstTimeStatus.defaultValue
        )
    }.flowOn(ioDispatcher)
}