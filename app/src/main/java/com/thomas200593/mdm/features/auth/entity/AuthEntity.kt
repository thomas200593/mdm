package com.thomas200593.mdm.features.auth.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thomas200593.mdm.features.user.entity.UserEntity

@Entity(
    tableName = "auth",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"], unique = true)], // ✅ Ensure one auth method per user
)
data class AuthEntity(
    @PrimaryKey(autoGenerate = true) val seqId : Long = 0,
    val userId: String,
    val authType: AuthType
)