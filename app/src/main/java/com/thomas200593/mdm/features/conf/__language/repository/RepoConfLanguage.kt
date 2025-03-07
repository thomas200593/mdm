package com.thomas200593.mdm.features.conf.__language.repository

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface RepoConfLanguage

class RepoImplConfLanguage @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : RepoConfLanguage