package com.gvelesiani.passworx.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.passworx.constants.PREVENT_TAKING_SCREENSHOTS
import com.gvelesiani.passworx.domain.repositories.local.ScreenshotsRepository

class ScreenshotsRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : ScreenshotsRepository {
    override fun preventTakingScreenshots(prevent: Boolean) {
        preferences.edit().putBoolean(PREVENT_TAKING_SCREENSHOTS, prevent).apply()
    }

    override fun getTakingScreenshotsStatus(): Boolean {
        return preferences.getBoolean(PREVENT_TAKING_SCREENSHOTS, false)
    }
}