package com.thomas200593.mdm.features.conf.__font_size.entity

import androidx.annotation.DrawableRes
import com.thomas200593.mdm.core.ui.common.AppIcons

enum class FontSize(
    val code: String,
    val sizeFactor: Int,
    @DrawableRes val icon: Int
) {
    SMALL(
        code = "conf_font_size_small",
        sizeFactor = -2,
        icon = AppIcons.FontSIze.small
    ),
    MEDIUM(
        code = "conf_font_size_medium",
        sizeFactor = 0,
        icon = AppIcons.FontSIze.medium
    ),
    LARGE(
        code = "conf_font_size_large",
        sizeFactor = 2,
        icon = AppIcons.FontSIze.large
    ),
    EXTRA_LARGE(
        code = "conf_font_size_extra_large",
        sizeFactor = 4,
        icon = AppIcons.FontSIze.x_large
    );

    companion object { val defaultValue: FontSize = MEDIUM }
}
