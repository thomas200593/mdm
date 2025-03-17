package com.thomas200593.mdm.features.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val seqId: Long = 0,
    val uid: String,
    val email: String
)
