package com.thomas200593.mdm.features.conf.ui

import com.thomas200593.mdm.features.conf.ui.accent.Accent
import com.thomas200593.mdm.features.conf.ui.dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf.ui.font_size.FontSize
import com.thomas200593.mdm.features.conf.ui.theme.Theme

data class ConfUi(
    val theme: Theme,
    val dynamicColor: DynamicColor,
    val accent: Accent,
    val fontSize: FontSize
)
