package com.thomas200593.mdm.features.conf.__theme.entity

enum class Theme {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        val defaultValue: Theme = SYSTEM
    }
}