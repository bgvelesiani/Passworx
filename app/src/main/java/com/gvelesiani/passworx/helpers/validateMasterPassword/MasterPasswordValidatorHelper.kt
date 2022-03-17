package com.gvelesiani.passworx.helpers.validateMasterPassword

interface MasterPasswordValidatorHelper {
    fun isValidPassword(data: String): Boolean
    fun getMasterPasswordErrors(): MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>
}

