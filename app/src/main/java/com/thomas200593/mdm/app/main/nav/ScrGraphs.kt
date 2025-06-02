package com.thomas200593.mdm.app.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.AppIcons
import kotlinx.serialization.Serializable

@Serializable object ScrGraphs {
    @Serializable data class Bootstrap(
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
        val iconRes: AppIcons.DestTopLevel,
        @StringRes val title: Int,
        @StringRes val description: Int
    ) {
        @Serializable data object Home : DestTopLevel(
            iconRes = AppIcons.DestTopLevel.Home,
            title = R.string.str_home,
            description = R.string.str_home_desc
        )
        @Serializable data object Menu : DestTopLevel(
            iconRes = AppIcons.DestTopLevel.Menu,
            title = R.string.str_menu,
            description = R.string.str_menu_desc
        )
    }
}