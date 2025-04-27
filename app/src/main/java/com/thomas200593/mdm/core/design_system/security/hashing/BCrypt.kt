package com.thomas200593.mdm.core.design_system.security.hashing

import at.favre.lib.crypto.bcrypt.BCrypt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class BCrypt @Inject constructor() {
    fun hash(string: String): String =
        BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, string.toCharArray())
    fun verify(string: String, hash: String): Boolean =
        BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(string.toCharArray(), hash).verified
}