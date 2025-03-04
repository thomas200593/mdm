package com.thomas200593.mdm.features.onboarding.entity

import com.thomas200593.mdm.features.conf.common.entity.Common

data class OnboardingScrData(
    val confCommon: Common,
    val list: List<Onboarding>,
    val listCurrentIndex: Int = 0,
    val listMaxIndex: Int = 0
)