package com.thomas200593.mdm.features.user.dao

import androidx.room.Dao
import androidx.room.Query
import com.thomas200593.mdm.features.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoUser {
    @Query("SELECT * FROM user WHERE 1=1 AND uid = :uid LIMIT 1")
    fun getOneByUid(uid: String) : Flow<List<UserEntity>>
    @Query("SELECT * FROM user WHERE 1=1 AND email = :email LIMIT 1")
    fun getOneByEmail(email: String) : Flow<List<UserEntity>>
}