package com.thomas200593.mdm.features.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Onboarding(
    @DrawableRes val imageRes: Int,
    @StringRes val title: Int,
    @StringRes val desc: Int
)