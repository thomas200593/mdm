package com.thomas200593.mdm.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomas200593.mdm.core.data.local.session.SessionEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        SessionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase()