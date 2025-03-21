package com.thomas200593.mdm.features.user.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["email"], unique = true)] // Ensures email is unique
)
data class UserEntity(
    @PrimaryKey val uid: String,
    val email: String
)