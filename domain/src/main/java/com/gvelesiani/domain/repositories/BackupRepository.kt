package com.gvelesiani.domain.repositories

interface BackupRepository {
    fun savePasswordsAsString(passwords: String)
    fun getPasswordsAsString(): String
}