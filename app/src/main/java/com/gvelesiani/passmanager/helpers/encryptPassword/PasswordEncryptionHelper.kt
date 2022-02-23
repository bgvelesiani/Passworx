package com.gvelesiani.passmanager.helpers.encryptPassword

interface PasswordEncryptionHelper {
    fun encryptPassword(strToEncrypt: String): ByteArray
    fun decryptPassword(dataToDecrypt: ByteArray): String
}