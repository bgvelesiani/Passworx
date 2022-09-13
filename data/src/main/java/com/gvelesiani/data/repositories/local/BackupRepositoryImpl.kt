package com.gvelesiani.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.common.constants.PASSWORDS_AS_STRING
import com.gvelesiani.domain.repositories.BackupRepository

class BackupRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : BackupRepository {
    override fun savePasswordsAsString(passwords: String) {
        preferences.edit().putString(PASSWORDS_AS_STRING, "").apply()
    }

    override fun getPasswordsAsString(): String {
        return preferences.getString(PASSWORDS_AS_STRING, "").toString()
    }
}