package com.gvelesiani.passworx.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.passworx.constants.BIOMETRICS_ALLOWED
import com.gvelesiani.passworx.domain.repositories.local.BiometricsRepository

class BiometricsRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : BiometricsRepository {
    override fun allowBiometrics(allow: Boolean) {
        preferences.edit().putBoolean(BIOMETRICS_ALLOWED, allow).apply()
    }

    override fun getBiometricsAllowingStatus(): Boolean {
        return preferences.getBoolean(BIOMETRICS_ALLOWED, false)
    }
}