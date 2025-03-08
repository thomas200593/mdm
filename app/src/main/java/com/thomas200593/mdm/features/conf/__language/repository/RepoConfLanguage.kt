package com.thomas200593.mdm.features.conf.__language.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.filterSort
import com.thomas200593.mdm.features.conf.__language.entity.Language
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.reflect.KProperty1

interface RepoConfLanguage {
    fun list(
        filter : (Language) -> Boolean = { true },
        vararg sortProperties : Pair<KProperty1<Language, Comparable<*>?>, Boolean>
    ) : Flow<List<Language>>
}

class RepoImplConfLanguage @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : RepoConfLanguage {
    private val source get() = Language.entries.toList()
    override fun list(
        filter: (Language) -> Boolean,
        vararg sortProperties: Pair<KProperty1<Language, Comparable<*>?>, Boolean>
    ): Flow<List<Language>> = flowOf(source.filterSort(filter, *sortProperties))
        .flowOn(ioDispatcher)
}