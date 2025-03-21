package com.thomas200593.mdm.features.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import com.thomas200593.mdm.core.data.local.database.AppDatabase
import com.thomas200593.mdm.features.user.entity.UserEntity
import javax.inject.Inject

@Dao
interface DaoUser {
    @Insert(entity = UserEntity::class, onConflict = ABORT)
    suspend fun insertUser(user : UserEntity) : Long
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String) : UserEntity?
}

class DaoUserImpl @Inject constructor(private val appDatabase: AppDatabase) : DaoUser {
    override suspend fun insertUser(user: UserEntity): Long = appDatabase.daoUser().insertUser(user)
    override suspend fun getUserByEmail(email: String): UserEntity? = appDatabase.daoUser().getUserByEmail(email)
}