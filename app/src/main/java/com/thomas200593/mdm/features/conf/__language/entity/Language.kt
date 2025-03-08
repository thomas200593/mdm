package com.thomas200593.mdm.features.conf.__language.entity

import com.thomas200593.mdm.features.conf.__country.entity.Country

enum class Language(
    val code: String,
    val country: Country
) {
    EN(
        code = "conf_language_en",
        country = Country.countryUS
    ),
    ID(
        code = "conf_language_id",
        country = Country.defaultValue
    );

    companion object {
        val defaultValue = EN
    }
}