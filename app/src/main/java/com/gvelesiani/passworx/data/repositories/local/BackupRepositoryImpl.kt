package com.gvelesiani.passworx.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.passworx.constants.PASSWORDS_AS_STRING
import com.gvelesiani.passworx.domain.repositories.local.BackupRepository

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