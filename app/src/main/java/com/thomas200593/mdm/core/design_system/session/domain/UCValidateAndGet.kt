package com.thomas200593.mdm.core.design_system.session.domain

import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.session.repository.RepoSession
import com.thomas200593.mdm.features.user.repository.RepoUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCValidateAndGet @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    private val repoSession : RepoSession,
    private val repoUser: RepoUser
) { operator fun invoke() = repoSession.getCurrent().flowOn(ioDispatcher).map {
    val session = it.getOrThrow()
    if(repoSession.isValid(session).getOrThrow()) session to repoUser.getOneByUid(session.userId).first().getOrThrow()
    else throw Throwable("Session Invalid")
}.catch { repoSession.archive(); repoSession.delete(); it } }