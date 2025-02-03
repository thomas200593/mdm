package com.thomas200593.mdm.features.conf._ui

import com.thomas200593.mdm.features.conf._accent.Accent
import com.thomas200593.mdm.features.conf._dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf._font_size.FontSize
import com.thomas200593.mdm.features.conf._theme.Theme

data class UI(
    val theme: Theme,
    val dynamicColor: DynamicColor,
    val accent: Accent,
    val fontSize: FontSize
)
