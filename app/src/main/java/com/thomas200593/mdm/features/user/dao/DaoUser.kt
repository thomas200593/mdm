package com.thomas200593.mdm.features.user.dao

import androidx.room.Dao
import javax.inject.Inject

@Dao
interface DaoUser

class DaoUserImpl @Inject constructor() : DaoUser