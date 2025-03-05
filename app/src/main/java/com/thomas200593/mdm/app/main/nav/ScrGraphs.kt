package com.thomas200593.mdm.app.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.AppIcons
import kotlinx.serialization.Serializable

@Serializable sealed interface ScrGraphs {
    @Serializable data object Initial
    @Serializable data object Onboarding
    @Serializable sealed class DestTopLevel(
        @DrawableRes val iconRes: Int,
        @StringRes val title: Int? = null,
        @StringRes val description: Int? = null
    ) : ScrGraphs {
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