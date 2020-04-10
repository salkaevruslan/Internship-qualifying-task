package org.jetbrains.internship

import java.io.File
import java.security.MessageDigest

fun checkHash(hashFile: File, currentFile: File, hashType: String): Boolean {
    val digest = MessageDigest.getInstance(hashType)
    val textBytes = currentFile.readBytes()
    digest.update(textBytes, 0, textBytes.size)
    return hashFile.readText() == digest.digest().toHexString()
}

fun getHashFileExtension(hashType: String): String {
    return when (hashType) {
        "SHA-1" -> ".sha1"
        "MD2" -> ".md2"
        "MD5" -> ".md5"
        "SHA-224" -> ".sha224"
        "SHA-256" -> ".sha256"
        "SHA-384" -> ".sha384"
        "SHA-512" -> ".sha512"
        else -> ".txt"
    }
}

fun ByteArray.toHexString(): String {
    return this.joinToString("") {
        java.lang.String.format("%02x", it)
    }
}

fun deleteOldFiles(dir: String, hashType: String) {
    File(dir).walkTopDown().forEach { currentPath ->
        if (".${currentPath.extension}" == getHashFileExtension(hashType)) {
            File(currentPath.absolutePath).delete()
        }
    }
}