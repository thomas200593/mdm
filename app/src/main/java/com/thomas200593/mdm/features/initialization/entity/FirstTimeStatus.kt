package com.thomas200593.mdm.features.initialization.entity

/**
 * Enum representing the first-time status of a user.
 *
 * This enum is used to track whether the user is accessing the application for the first time.
 *
 * @property code The string representation of the status.
 */
enum class FirstTimeStatus(val code: String) {
    /** Indicates that the user is accessing the application for the first time. */
    YES(code = "first_time_status_yes"),
    /** Indicates that the user has already accessed the application before. */
    NO(code = "first_time_status_no");
    companion object {
        /** The default status value, set to [YES]. */
        val defaultValue: FirstTimeStatus = YES
    }
}