package com.thomas200593.mdm.core.design_system.security.hashing

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BCrypt @Inject constructor() {
    fun hash(string: String): String {
        return ""
    }
    fun verify(string: String, hash: String): Boolean {
        return false
    }
}