package com.gvelesiani.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.data.common.MASTER_PASSWORD
import com.gvelesiani.domain.repositories.MasterPasswordRepository

class MasterPasswordRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : MasterPasswordRepository {
    override fun createOrChangeMasterPassword(masterPassword: String) {
        preferences.edit().putString(MASTER_PASSWORD, masterPassword).apply()
    }

    override fun getMasterPassword(): String {
        return preferences.getString(MASTER_PASSWORD, "").toString()
    }
}