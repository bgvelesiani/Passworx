package com.gvelesiani.passworx.domain.repositories.local

interface BackupRepository {
    fun savePasswordsAsString(passwords: String)
    fun getPasswordsAsString(): String
}