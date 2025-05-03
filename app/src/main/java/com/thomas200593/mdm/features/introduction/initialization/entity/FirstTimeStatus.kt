package com.thomas200593.mdm.features.introduction.initialization.entity

enum class FirstTimeStatus(val code: String) {
    YES(code = "first_time_status_yes"),
    NO(code = "first_time_status_no");
    companion object { val defaultValue: FirstTimeStatus = YES }
}