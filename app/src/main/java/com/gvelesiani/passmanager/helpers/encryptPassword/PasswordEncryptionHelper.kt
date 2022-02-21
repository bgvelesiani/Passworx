package com.gvelesiani.passmanager.helpers.encryptPassword

import android.content.Context

interface PasswordEncryptionHelper {
    fun encryptPassword(context: Context, strToEncrypt: String): ByteArray
    fun decryptPassword(context: Context, dataToDecrypt: ByteArray): String
}