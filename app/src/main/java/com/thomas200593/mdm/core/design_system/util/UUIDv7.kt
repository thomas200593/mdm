package com.thomas200593.mdm.core.design_system.util

import java.security.SecureRandom

/**
 * UUIDv7 Zero Dependencies
 * @author https://antonz.org/uuidv7
 */
object UUIDv7 {
    private val random = SecureRandom()
    fun generateAsBytes(): ByteArray {
        val timestamp = Constants.NOW_EPOCH_MILLISECOND
        val randomBytes = ByteArray(10).apply { random.nextBytes(this) }
        return byteArrayOf(
            (timestamp shr 40).toByte(), (timestamp shr 32).toByte(), (timestamp shr 24).toByte(),
            (timestamp shr 16).toByte(), (timestamp shr 8).toByte(), timestamp.toByte(),
            (randomBytes[0].toInt() and 0x0F or 0x70).toByte(), // Version 7
            randomBytes[1], (randomBytes[2].toInt() and 0x3F or 0x80).toByte(), // Variant IETF
            *randomBytes.copyOfRange(3, 10)
        )
    }
    fun generateAsString(): String = generateAsBytes().toUUIDString()
    fun extractTimestamp(uuidBytes: ByteArray): Long = uuidBytes
        .also { require(it.size == 16) { "Invalid UUID: must be 16 bytes" } }.toLong(0, 6)
    fun fromUUIDString(uuid: String): ByteArray = uuid.replace("-", "")
        .also { require(it.length == 32) { "Invalid UUID format: must be 32 hex characters" } }
        .chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    private fun ByteArray.toUUIDString(): String =
        also { require(size == 16) { "Invalid UUID: must be 16 bytes" } }
            .joinToString("") { "%02x".format(it) }
                .replace(Regex("(.{8})(.{4})(.{4})(.{4})(.{12})"), "$1-$2-$3-$4-$5")
    private fun ByteArray.toLong(startIndex: Int, length: Int): Long =
        also { require(length in 1..8) { "Cannot convert more than 8 bytes to Long" } }
            .also { require(startIndex + length <= size) { "ByteArray out of bounds" } }
            .sliceArray(startIndex until startIndex + length)
            .fold(0L) { acc, byte -> (acc shl 8) or (byte.toLong() and 0xFF) }
}