package com.gvelesiani.passworx.helpers.encryptPassword

interface PasswordEncryptionHelper {
    fun encrypt(cleartext: String): String
    fun decrypt(encrypted: String): String
}