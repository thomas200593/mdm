package com.thomas200593.mdm.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.thomas200593.mdm.core.data.local.database.entity_common.TypeConverterAuditTrail
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.TypeConverterAuthType
import com.thomas200593.mdm.features.user.dao.DaoUser
import com.thomas200593.mdm.features.user.entity.UserEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        UserEntity::class,
        AuthEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        TypeConverterAuditTrail::class, TypeConverterAuthType::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoUser() : DaoUser
    abstract fun daoAuth() : DaoAuth
}