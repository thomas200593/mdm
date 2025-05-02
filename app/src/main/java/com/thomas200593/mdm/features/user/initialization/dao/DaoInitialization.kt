package com.thomas200593.mdm.features.user.initialization.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.thomas200593.mdm.features.user.initialization.entity.DTOInitializationResult
import com.thomas200593.mdm.features.security.auth.entity.AuthEntity
import com.thomas200593.mdm.features.user.user.entity.UserEntity
import com.thomas200593.mdm.features.user.user_profile.entity.UserProfileEntity
import com.thomas200593.mdm.features.user.user_role.entity.UserRoleEntity

@Dao interface DaoInitialization {
    @Insert(entity = UserEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserEntity) : Long
    @Insert(entity = UserProfileEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun addUserProfile(profile: UserProfileEntity) : Long
    @Insert(entity = AuthEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun addAuth(auth: AuthEntity) : Long
    @Insert(entity = UserRoleEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun addUserRole(roles: List<UserRoleEntity>) : List<Long>
    @Delete(entity = UserEntity::class)
    suspend fun rollback(user: UserEntity) : Int
    @Transaction suspend fun insertInitialization(user: UserEntity, profile: UserProfileEntity, auth: AuthEntity, roles: List<UserRoleEntity>): DTOInitializationResult {
        val userId = addUser(user = user)
        val profileId = addUserProfile(profile = profile)
        val authId = addAuth(auth = auth)
        val rolesIds = addUserRole(roles = roles)
        return DTOInitializationResult(
            userId = userId, user = user,
            profileId = profileId, profile = profile,
            authId = authId, auth = auth,
            rolesIds = rolesIds, roles = roles
        )
    }
}