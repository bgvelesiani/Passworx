package com.gvelesiani.helpers.helpers.generatePassword

interface PasswordGeneratorHelper {
    fun generatePassword(length: Int, properties: String): String
}