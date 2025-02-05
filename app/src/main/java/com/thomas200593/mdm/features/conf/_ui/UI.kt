package com.thomas200593.mdm.features.conf._ui

import com.thomas200593.mdm.features.conf.__contrast_accent.ContrastAccent
import com.thomas200593.mdm.features.conf.__dynamic_color.DynamicColor
import com.thomas200593.mdm.features.conf.__font_size.FontSize
import com.thomas200593.mdm.features.conf.__theme.Theme

data class UI(
    val theme: Theme,
    val contrastAccent: ContrastAccent,
    val dynamicColor: DynamicColor,
    val fontSize: FontSize
)