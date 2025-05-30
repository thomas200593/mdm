package com.thomas200593.mdm.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thomas200593.mdm.core.data.local.database.entity_common.TypeConverterAuditTrail
import com.thomas200593.mdm.core.design_system.session.dao.DaoSession
import com.thomas200593.mdm.core.design_system.session.dao.DaoSessionHistory
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionHistoryEntity
import com.thomas200593.mdm.core.design_system.session.entity.TypeConverterSession
import com.thomas200593.mdm.features.auth.dao.DaoAuth
import com.thomas200593.mdm.features.auth.entity.AuthEntity
import com.thomas200593.mdm.features.auth.entity.TypeConverterAuthType
import com.thomas200593.mdm.features.introduction.initialization.dao.DaoInitialization
import com.thomas200593.mdm.features.management.role.dao.DaoRole
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.features.management.role.entity.TypeConverterRoleType
import com.thomas200593.mdm.features.management.user.dao.DaoUser
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.tld.user_profile.dao.DaoUserProfile
import com.thomas200593.mdm.features.tld.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.management.user_role.dao.DaoUserRole
import com.thomas200593.mdm.features.management.user_role.entity.UserRoleEntity
import javax.inject.Singleton

@Singleton @Database(
    entities = [
        UserEntity::class, UserProfileEntity::class, UserRoleEntity::class, AuthEntity::class,
        RoleEntity::class, SessionEntity::class, SessionHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        TypeConverterAuditTrail::class, TypeConverterAuthType::class, TypeConverterSession::class,
        TypeConverterRoleType::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoInitialization() : DaoInitialization
    abstract fun daoSession() : DaoSession
    abstract fun daoSessionHistory() : DaoSessionHistory
    abstract fun daoUser() : DaoUser
    abstract fun daoUserProfile() : DaoUserProfile
    abstract fun daoUserRole() : DaoUserRole
    abstract fun daoAuth() : DaoAuth
    abstract fun daoRole() : DaoRole
}