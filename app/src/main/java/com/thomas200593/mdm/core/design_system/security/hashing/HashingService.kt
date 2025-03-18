package com.thomas200593.mdm.core.design_system.security.hashing

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

enum class HashingAlgorithm { BCrypt, MD5, SHA256 }

interface HashingService {
    fun hash(string: String, algorithm: HashingAlgorithm): String
    fun verify(string: String, hash: String, algorithm: HashingAlgorithm): Boolean
}

@Singleton
class HashingServiceImpl @Inject constructor(
) : HashingService {
    override fun hash(string: String, algorithm: HashingAlgorithm) : String = when(algorithm) {
        HashingAlgorithm.BCrypt -> { "" }
        HashingAlgorithm.MD5 -> hashWith("MD5", string)
        HashingAlgorithm.SHA256 -> hashWith("SHA-256", string)
    }
    override fun verify(string: String, hash: String, algorithm: HashingAlgorithm): Boolean = when(algorithm) {
        HashingAlgorithm.BCrypt -> { false }
        HashingAlgorithm.MD5 -> hashWith("MD5", string) == hash
        HashingAlgorithm.SHA256 -> hashWith("SHA-256", string) == hash
    }
    private fun hashWith(algorithm: String, input: String): String = MessageDigest
        .getInstance(algorithm).digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
}