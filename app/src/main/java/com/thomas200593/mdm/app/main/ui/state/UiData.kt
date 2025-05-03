package com.thomas200593.mdm.app.main.ui.state

import com.thomas200593.mdm.features.common.cnf_ui_contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.common.cnf_ui_font_size.entity.FontSize

data class UiData(
    val darkThemeEnabled: Boolean,
    val dynamicColorEnabled: Boolean,
    val contrastAccent: ContrastAccent,
    val fontSize: FontSize
)