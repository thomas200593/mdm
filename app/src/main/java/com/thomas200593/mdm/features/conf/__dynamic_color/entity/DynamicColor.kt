package com.thomas200593.mdm.features.conf.__dynamic_color.entity

enum class DynamicColor(val code: String) {
    DISABLED(code = "conf_dynamic_color_disabled"),
    ENABLED(code = "conf_dynamic_color_enabled");

    companion object {
        val defaultValue: DynamicColor = DISABLED
    }
}