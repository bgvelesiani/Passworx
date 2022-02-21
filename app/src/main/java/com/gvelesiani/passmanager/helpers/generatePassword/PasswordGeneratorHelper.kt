package com.gvelesiani.passmanager.helpers.generatePassword

import com.gvelesiani.passmanager.constants.PasswordProperties

interface PasswordGeneratorHelper {
    fun generatePassword(length: Int, properties: String): String
}