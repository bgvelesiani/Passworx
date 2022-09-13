package com.gvelesiani.domain.repositories

interface MasterPasswordRepository {
    fun createOrChangeMasterPassword(masterPassword: String)
    fun getMasterPassword(): String
}