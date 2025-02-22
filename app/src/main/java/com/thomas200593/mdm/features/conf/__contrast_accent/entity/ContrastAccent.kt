package com.thomas200593.mdm.features.conf.__contrast_accent.entity

enum class ContrastAccent(
    val code: String
) {
    DEFAULT(
        code = "conf_contrast_accent_default"
    ),
    MEDIUM(
        code = "conf_contrast_accent_medium"
    ),
    HIGH(
        code = "conf_contrast_accent_high"
    );

    companion object {
        val defaultValue: ContrastAccent = DEFAULT
    }
}