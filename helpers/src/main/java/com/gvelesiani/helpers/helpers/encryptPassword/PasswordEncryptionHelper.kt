package com.gvelesiani.helpers.helpers.encryptPassword

interface PasswordEncryptionHelper {
    fun encrypt(cleartext: String): String
    fun decrypt(encrypted: String): String
}