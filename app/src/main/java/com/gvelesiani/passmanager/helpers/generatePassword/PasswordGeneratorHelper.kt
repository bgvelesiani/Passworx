package com.gvelesiani.passmanager.helpers.generatePassword

interface PasswordGeneratorHelper {
    fun generatePassword(length: Int, properties: String): String
}