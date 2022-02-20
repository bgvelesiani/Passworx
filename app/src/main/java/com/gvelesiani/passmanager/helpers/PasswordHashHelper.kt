package com.gvelesiani.passmanager.helpers

import android.content.Context

interface PasswordHashHelper {
    fun encryptPassword(context: Context, strToEncrypt: String): ByteArray
    fun decryptPassword(context: Context, dataToDecrypt: ByteArray): String
}