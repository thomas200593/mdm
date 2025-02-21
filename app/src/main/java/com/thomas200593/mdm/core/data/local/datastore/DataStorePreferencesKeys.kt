package com.thomas200593.mdm.core.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKeys {
    val dsKeyFirstTimeStatus = stringPreferencesKey("DS_KEY_FIRST_TIME_STATUS")
    val dsKeyOnboardingStatus = stringPreferencesKey("DS_KEY_ONBOARDING_STATUS")
    val dsKeyTheme = stringPreferencesKey("DS_KEY_THEME")
    val dsKeyContrastAccent = stringPreferencesKey("DS_KEY_CONTRAST_ACCENT")
    val dsKeyDynamicColor = stringPreferencesKey("DS_KEY_DYNAMIC_COLOR")
    val dsKeyFontSize = stringPreferencesKey("DS_KEY_FONT_SIZE")
    val dsKeyCountry = stringPreferencesKey("DS_KEY_COUNTRY")
    val dsKeyLanguage = stringPreferencesKey("DS_KEY_LANGUAGE")
}