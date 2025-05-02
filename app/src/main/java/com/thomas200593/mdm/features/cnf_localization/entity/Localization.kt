package com.thomas200593.mdm.features.cnf_localization.entity

import com.thomas200593.mdm.features.cnf_localization_country.entity.Country
import com.thomas200593.mdm.features.cnf_localization_language.entity.Language

data class Localization(
    val country: Country,
    val language: Language
)