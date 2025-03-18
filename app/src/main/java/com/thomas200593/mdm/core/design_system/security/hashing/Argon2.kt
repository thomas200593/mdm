package com.thomas200593.mdm.core.design_system.security.hashing

import de.mkammerer.argon2.Argon2Factory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Argon2 @Inject constructor() {
    private val argon2 by lazy { Argon2Factory.create() }
    fun hash(string: String): String {
        val charArray = string.toCharArray()
        return try { argon2.hash(3, 65536, 1, charArray) } finally { charArray.fill('\u0000') }
    }
    fun verify(string: String, hash: String): Boolean {
        val charArray = string.toCharArray()
        return try { argon2.verify(hash, charArray) } finally { charArray.fill('\u0000') }
    }
}