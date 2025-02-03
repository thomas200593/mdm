package com.thomas200593.mdm.features.conf._conf_ui

import com.thomas200593.mdm.features.conf._conf_ui_accent.Accent
import com.thomas200593.mdm.features.conf._conf_ui_dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf._conf_ui_font_size.FontSize
import com.thomas200593.mdm.features.conf._conf_ui_theme.Theme

data class UI(
    val theme: Theme,
    val dynamicColor: DynamicColor,
    val accent: Accent,
    val fontSize: FontSize
)
