package com.gvelesiani.passworx.helpers.generatePassword

interface PasswordGeneratorHelper {
    fun generatePassword(length: Int, properties: String): String
}