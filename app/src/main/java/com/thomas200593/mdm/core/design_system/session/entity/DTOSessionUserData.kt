package com.thomas200593.mdm.core.design_system.session.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.thomas200593.mdm.features.role.role.entity.RoleEntity
import com.thomas200593.mdm.features.user.user.entity.UserEntity
import com.thomas200593.mdm.features.user.user_profile.entity.UserProfileEntity

data class DTOSessionUserData(
    @Embedded val session: SessionEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "uid"
    ) val user: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    ) val profile: UserProfileEntity,
    @Relation(
        parentColumn = "current_role_code",
        entityColumn = "role_code"
    ) val currentRole: RoleEntity?
)
