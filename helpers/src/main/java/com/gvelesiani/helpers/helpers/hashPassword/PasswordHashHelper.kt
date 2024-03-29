package com.gvelesiani.helpers.helpers.hashPassword

interface PasswordHashHelper {
    fun hash(password: String): String
    fun verify(password: String, bcryptHashString: String): Boolean
}