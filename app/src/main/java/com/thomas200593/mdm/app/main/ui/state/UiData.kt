package com.thomas200593.mdm.app.main.ui.state

import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent

data class UiData(
    val darkThemeEnabled: Boolean,
    val dynamicColorEnabled: Boolean,
    val contrastAccent: ContrastAccent
)