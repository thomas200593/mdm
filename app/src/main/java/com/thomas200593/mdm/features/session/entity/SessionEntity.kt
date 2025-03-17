package com.thomas200593.mdm.features.session.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "session"
)
data class SessionEntity(
    @PrimaryKey
    val id: String
)