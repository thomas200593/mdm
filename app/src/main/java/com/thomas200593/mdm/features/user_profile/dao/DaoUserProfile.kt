package com.thomas200593.mdm.features.user_profile.dao

import androidx.room.Dao
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@Dao
interface DaoUserProfile
class DaoUserProfileImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DaoUserProfile