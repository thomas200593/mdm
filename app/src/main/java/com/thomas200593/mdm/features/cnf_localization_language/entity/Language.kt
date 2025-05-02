package com.thomas200593.mdm.features.cnf_localization_language.entity

import com.thomas200593.mdm.features.cnf_localization_country.entity.Country

enum class Language(
    val code: String,
    val country: Country
) {
    EN(
        code = "en",
        country = Country.Companion.countryUS
    ),
    ID(
        code = "in",
        country = Country.Companion.defaultValue
    );

    companion object { val defaultValue = EN }
}