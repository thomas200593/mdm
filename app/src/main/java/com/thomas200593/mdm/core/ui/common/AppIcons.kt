package com.thomas200593.mdm.core.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import com.thomas200593.mdm.R
import kotlinx.serialization.Serializable

object AppIcons {
    object App {
        @DrawableRes val icon = +R.drawable.app_icon_48x48px
    }
    @Serializable sealed interface DestTopLevel {
        @Serializable data object Dashboard : DestTopLevel
        @Serializable data object UserProfile : DestTopLevel
    }
    fun mapTopLevelToMaterialIcons(appIcon : DestTopLevel) = when (appIcon) {
        DestTopLevel.Dashboard -> Icons.Default.Dashboard
        DestTopLevel.UserProfile -> Icons.Default.Person
    }
    object FontSize {
        @DrawableRes val small = +R.drawable.app_icon_text_sm_48px
        @DrawableRes val medium = +R.drawable.app_icon_text_md_48px
        @DrawableRes val large = +R.drawable.app_icon_text_lg_48px
        @DrawableRes val x_large = +R.drawable.app_icon_text_xl_48px
    }
}