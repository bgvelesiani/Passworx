package com.gvelesiani.helpers.helpers.validateMasterPassword

interface MasterPasswordValidatorHelper {
    fun isValidPassword(data: String): Boolean
    fun getMasterPasswordErrors(): MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>
}

