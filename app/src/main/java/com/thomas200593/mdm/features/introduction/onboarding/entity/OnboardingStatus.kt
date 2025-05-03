package com.thomas200593.mdm.features.introduction.onboarding.entity

enum class OnboardingStatus(val code: String) {
    SHOW(code = "onboarding_status_show"),
    HIDE(code = "onboarding_status_hide");
    companion object { val defaultValue: OnboardingStatus = SHOW }
}