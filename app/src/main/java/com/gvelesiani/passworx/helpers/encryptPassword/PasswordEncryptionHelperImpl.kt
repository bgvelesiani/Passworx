package com.gvelesiani.passworx.helpers.encryptPassword

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class PasswordEncryptionHelperImpl : PasswordEncryptionHelper {
    private val keyValue = byteArrayOf(
        'p'.code.toByte(),
        'a'.code.toByte(),
        's'.code.toByte(),
        's'.code.toByte(),
        'w'.code.toByte(),
        'o'.code.toByte(),
        'r'.code.toByte(),
        'x'.code.toByte(),
        'g'.code.toByte(),
        'v'.code.toByte(),
        'e'.code.toByte(),
        'l'.code.toByte(),
        'e'.code.toByte(),
        's'.code.toByte(),
        'i'.code.toByte(),
        'a'.code.toByte()
    )

    @Throws(Exception::class)
    override fun encrypt(cleartext: String): String {
        val rawKey = rawKey
        val result = encrypt(rawKey, cleartext.toByteArray())
        return toHex(result)
    }

    @Throws(Exception::class)
    override fun decrypt(encrypted: String): String {
        val enc = toByte(encrypted)
        val result = decrypt(enc)
        return String(result)
    }

    @get:Throws(Exception::class)
    private val rawKey: ByteArray
        get() {
            val key: SecretKey =
                SecretKeySpec(keyValue, "AES")
            return key.encoded
        }

    @SuppressLint("GetInstance")
    @Throws(Exception::class)
    private fun encrypt(raw: ByteArray, clear: ByteArray): ByteArray {
        val skeySpec: SecretKey = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        return cipher.doFinal(clear)
    }

    @SuppressLint("GetInstance")
    @Throws(Exception::class)
    private fun decrypt(encrypted: ByteArray): ByteArray {
        val skeySpec: SecretKey =
            SecretKeySpec(keyValue, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return cipher.doFinal(encrypted)
    }

    private fun toByte(hexString: String): ByteArray {
        val len = hexString.length / 2
        val result = ByteArray(len)
        for (i in 0 until len) result[i] = Integer.valueOf(
            hexString.substring(2 * i, 2 * i + 2),
            16
        ).toByte()
        return result
    }

    private fun toHex(buf: ByteArray?): String {
        if (buf == null) return ""
        val result = StringBuffer(2 * buf.size)
        for (i in buf.indices) {
            appendHex(result, buf[i].toInt())
        }
        return result.toString()
    }

    private val HEX = "0123456789ABCDEF"
    private fun appendHex(sb: StringBuffer, b: Int) {
        sb.append(HEX[b shr 4 and 0x0f]).append(HEX[b and 0x0f])
    }
}