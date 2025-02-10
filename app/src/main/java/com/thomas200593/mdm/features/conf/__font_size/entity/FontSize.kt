package com.thomas200593.mdm.features.conf.__font_size.entity

enum class FontSize {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE;

    companion object {
        val defaultValue: FontSize = MEDIUM
    }
}
