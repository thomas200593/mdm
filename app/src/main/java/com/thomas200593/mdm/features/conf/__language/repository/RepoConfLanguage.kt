package com.thomas200593.mdm.features.conf.__language.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mdm.core.data.local.datastore.DataStorePreferencesKeys
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.filterSort
import com.thomas200593.mdm.features.conf.__language.entity.Language
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.KProperty1

interface RepoConfLanguage {
    fun list(
        filter : (Language) -> Boolean = { true },
        vararg sortProperties : Pair<KProperty1<Language, Comparable<*>?>, Boolean>
    ) : Flow<List<Language>>
    suspend fun set(language: Language)
}

class RepoImplConfLanguage @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(CoroutineDispatchers.Main) private val mainDispatcher: CoroutineDispatcher,
    private val datastore: DataStorePreferences
) : RepoConfLanguage {
    private val source get() = Language.entries.toList()
    override fun list(
        filter: (Language) -> Boolean,
        vararg sortProperties: Pair<KProperty1<Language, Comparable<*>?>, Boolean>
    ): Flow<List<Language>> = flowOf(source.filterSort(filter, *sortProperties)).flowOn(ioDispatcher)
    override suspend fun set(language: Language) {
        withContext(ioDispatcher) { datastore.instance.edit { it[DataStorePreferencesKeys.dsKeyLanguage] = language.name } }
        withContext(mainDispatcher) { AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(Locale(language.code))) }
    }
}