package com.thomas200593.mdm.features.conf._ui

import com.thomas200593.mdm.features.conf.__accent.Accent
import com.thomas200593.mdm.features.conf.__dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf.__font_size.FontSize
import com.thomas200593.mdm.features.conf.__theme.Theme

data class UI(
    val theme: Theme,
    val dynamicColor: DynamicColor,
    val accent: Accent,
    val fontSize: FontSize
)
