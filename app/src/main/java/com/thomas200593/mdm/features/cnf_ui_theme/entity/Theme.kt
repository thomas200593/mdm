package com.thomas200593.mdm.features.cnf_ui_theme.entity

enum class Theme(
    val code: String
) {
    SYSTEM(
        code = "conf_theme_system"
    ),
    LIGHT(
        code = "conf_theme_light"
    ),
    DARK(
        code = "conf_theme_dark"
    );

    companion object { val defaultValue: Theme = SYSTEM }
}