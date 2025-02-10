package com.thomas200593.mdm.features.conf._ui.entity

import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize
import com.thomas200593.mdm.features.conf.__theme.entity.Theme

data class UI(
    val theme: Theme,
    val contrastAccent: ContrastAccent,
    val dynamicColor: DynamicColor,
    val fontSize: FontSize
)