package com.gvelesiani.passmanager.helpers.encryptPassword

import android.content.Context
import android.preference.PreferenceManager
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class PasswordEncryptionHelperImpl(private val context: Context) : PasswordEncryptionHelper {
    override fun encryptPassword(strToEncrypt: String): ByteArray {
        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key = keygen.generateKey()
        saveSecretKey(key)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        saveInitializationVector(cipher.iv)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toInt().toChar())
        }

        return cipherText
    }

    override fun decryptPassword(dataToDecrypt: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(getSavedInitializationVector())
        cipher.init(Cipher.DECRYPT_MODE, getSavedSecretKey(), ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toInt().toChar())
        }

        return sb.toString()
    }

    private fun saveSecretKey(secretKey: SecretKey) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(secretKey)
        val strToSave = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("secret_key", strToSave)
        editor.apply()
    }

    private fun getSavedSecretKey(): SecretKey {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strSecretKey = sharedPref.getString("secret_key", "")
        val bytes = Base64.decode(strSecretKey, Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        return ois.readObject() as SecretKey
    }

    private fun saveInitializationVector(initializationVector: ByteArray) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(initializationVector)
        val strToSave = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("initialization_vector", strToSave)
        editor.apply()
    }

    private fun getSavedInitializationVector(): ByteArray {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strInitializationVector = sharedPref.getString("initialization_vector", "")
        val bytes = Base64.decode(strInitializationVector, Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        return ois.readObject() as ByteArray
    }

}