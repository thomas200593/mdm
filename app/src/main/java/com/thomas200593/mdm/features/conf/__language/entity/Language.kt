package com.thomas200593.mdm.features.conf.__language.entity

enum class Language(
    val code: String
) {
    EN(
        code = "conf_language_en"
    ),
    ID(
        code = "conf_language_id"
    );

    companion object { val defaultValue = EN }
}