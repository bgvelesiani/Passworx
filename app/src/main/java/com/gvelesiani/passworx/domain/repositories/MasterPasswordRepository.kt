package com.gvelesiani.passworx.domain.repositories.local

interface MasterPasswordRepository {
    fun createOrChangeMasterPassword(masterPassword: String)
    fun getMasterPassword(): String
}