package com.thomas200593.mdm.features.conf.common.repository

import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__theme.entity.Theme
import com.thomas200593.mdm.features.conf._locale.entity.Locale
import com.thomas200593.mdm.features.conf._ui.entity.UI
import com.thomas200593.mdm.features.conf.common.entity.Common
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepoConfCommon {
    val confCommon: Flow<Common>
}

internal class RepoConfCommonImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    dataStore: DataStorePreferences
) : RepoConfCommon {
    override val confCommon: Flow<Common> = dataStore.instance.data.map { data ->
        Common(
            ui = UI(
                theme = data[DataStorePreferencesKeys.dsKeyTheme]
                    ?.let { Theme.valueOf(it) } ?: Theme.SYSTEM,
                contrastAccent = data[DataStorePreferencesKeys.dsKeyContrastAccent]
                    ?.let { ContrastAccent.valueOf(it) } ?: ContrastAccent.DEFAULT,
                dynamicColor = data[DataStorePreferencesKeys.dsKeyDynamicColor]
                    ?.let { DynamicColor.valueOf(it) } ?: DynamicColor.DISABLED,
                fontSize = data[DataStorePreferencesKeys.dsKeyFontSize]
                    ?.let { FontSize.valueOf(it) } ?: FontSize.MEDIUM
            ),
            locale = Locale(
                language = Language.EN
            )
        )
    }.flowOn(ioDispatcher)
}