package com.thomas200593.mdm.app.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.AppIcons
import kotlinx.serialization.Serializable

@Serializable
sealed interface ScrGraphs {
    @Serializable sealed class DestTopLevel(
        val usesAuth: Boolean = true,
        @DrawableRes val selectedIcon: Int,
        @DrawableRes val unselectedIcon: Int = selectedIcon,
        @StringRes val title: Int,
        @StringRes val desc: Int
    ) : ScrGraphs {
        @Serializable data object Dashboard : DestTopLevel(
            selectedIcon = AppIcons.DestTopLevel.dashboard,
            title = R.string.str_dashboard,
            desc = R.string.str_dashboard_desc
        )
        @Serializable data object UserProfile : DestTopLevel(
            selectedIcon = AppIcons.DestTopLevel.user_profile,
            title = R.string.str_user_profile,
            desc = R.string.str_user_profile_desc
        )
    }
}