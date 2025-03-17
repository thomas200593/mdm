package com.thomas200593.mdm.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomas200593.mdm.features.session.entity.SessionEntity
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.user.entity.UserEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        UserEntity::class,
        AuthEntity::class,
        SessionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase()