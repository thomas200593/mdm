package com.thomas200593.mdm.features.common.cnf_ui.entity

import com.thomas200593.mdm.features.common.cnf_ui_contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.common.cnf_ui_dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.common.cnf_ui_font_size.entity.FontSize
import com.thomas200593.mdm.features.common.cnf_ui_theme.entity.Theme

data class UI(
    val theme: Theme,
    val contrastAccent: ContrastAccent,
    val dynamicColor: DynamicColor,
    val fontSize: FontSize
)