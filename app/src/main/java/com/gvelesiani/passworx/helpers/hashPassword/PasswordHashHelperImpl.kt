package com.gvelesiani.passworx.helpers.hashPassword

import at.favre.lib.crypto.bcrypt.BCrypt

class PasswordHashHelperImpl : PasswordHashHelper {
    override fun hash(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    override fun verify(password: String, bcryptHashString: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified
    }
}
