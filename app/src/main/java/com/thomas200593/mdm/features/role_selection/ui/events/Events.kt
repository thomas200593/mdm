package com.thomas200593.mdm.features.role_selection.ui.events

import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.features.management.role.entity.RoleEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEntity
import com.thomas200593.mdm.core.design_system.session.entity.SessionEvent
import com.thomas200593.mdm.features.management.user.entity.UserEntity
import com.thomas200593.mdm.features.user_profile.entity.UserProfileEntity

sealed interface Events {
    sealed interface Session : Events {
        data class Loading(val ev : SessionEvent.Loading) : Session
        data class Invalid(val ev: SessionEvent.Invalid, val error: Error) : Session
        data class Valid(
            val ev: SessionEvent, val data: Triple<UserEntity, UserProfileEntity, SessionEntity>,
            val currentRole: RoleEntity?
        ) : Session
    }
    sealed interface TopBar : Events {
        sealed interface BtnScrDesc : TopBar {
            data object Clicked : BtnScrDesc
            data object Dismissed : BtnScrDesc
        }
        sealed interface BtnSignOut : TopBar {
            data object Clicked : BtnSignOut
        }
    }
    sealed interface Content : Events {
        sealed interface Form : Content {
            data class SelectedRole(val role : RoleEntity) : Form
            sealed interface SearchBar : Form {
                data class QueryChanged(val query : String) : SearchBar
            }
            sealed interface LayoutType : Form {
                data object List : LayoutType
                data object Grid : LayoutType
            }
            sealed interface ModalBottomSheet : Form {
                data object Clicked : ModalBottomSheet
                data object Dismissed : ModalBottomSheet
                data object Applied : ModalBottomSheet
            }
        }
    }
    sealed interface BottomBar : Events {
        sealed interface BtnConfirmRole : BottomBar {
            data class Clicked(val role: RoleEntity) : BtnConfirmRole
        }
        sealed interface BtnRoleInfo : BottomBar {
            data class Clicked(val role: RoleEntity) : BtnRoleInfo
            data object Dismissed : BtnRoleInfo
        }
    }
}