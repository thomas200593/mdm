package com.thomas200593.mdm.features.auth.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.thomas200593.mdm.features.user.entity.UserEntity

@Entity(
    tableName = "auth",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["seqId"],
            childColumns = ["userId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AuthEntity(
    @PrimaryKey val userId: Long,
    val password: String
)
