package com.thomas200593.mdm.app.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.AppIcons
import kotlinx.serialization.Serializable

@Serializable object ScrGraphs {
    @Serializable data class Initial(
        @StringRes val title: Int = R.string.str_initial,
        @StringRes val description: Int = R.string.str_initial
    )
    @Serializable data class Onboarding(
        @StringRes val title: Int = R.string.str_onboarding,
        @StringRes val description: Int = R.string.str_onboarding
    )
    @Serializable data class Initialization(
        @StringRes val title: Int = R.string.str_initialization,
        @StringRes val description: Int = R.string.str_initialization
    )
    @Serializable data class Auth(
        @StringRes val title: Int = R.string.str_auth,
        @StringRes val description: Int = R.string.str_auth
    )
    @Serializable data class RoleSelection(
        @StringRes val title: Int = R.string.str_role_selection,
        @StringRes val description: Int = R.string.str_role_selection
    )
    @Serializable sealed class DestTopLevel(
        @DrawableRes val iconRes: Int,
        @StringRes val title: Int,
        @StringRes val description: Int
    ) {
        @Serializable data object Dashboard : DestTopLevel(
            iconRes = AppIcons.DestTopLevel.dashboard,
            title = R.string.str_dashboard,
            description = R.string.str_dashboard_desc
        )
        @Serializable data object UserProfile : DestTopLevel(
            iconRes = AppIcons.DestTopLevel.user_profile,
            title = R.string.str_user_profile,
            description = R.string.str_user_profile_desc
        )
    }
}