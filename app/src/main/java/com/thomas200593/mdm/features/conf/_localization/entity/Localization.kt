package com.thomas200593.mdm.features.conf._localization.entity

import com.thomas200593.mdm.features.conf.__country.entity.Country
import com.thomas200593.mdm.features.conf.__language.entity.Language

data class Localization(
    val country: Country,
    val language: Language
)