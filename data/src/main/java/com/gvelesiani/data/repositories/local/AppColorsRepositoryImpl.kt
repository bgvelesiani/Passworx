package com.gvelesiani.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.common.constants.APP_THEME_KEY
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.domain.repositories.AppColorsRepository

class AppColorsRepositoryImpl (private val preferences: SharedPreferences): AppColorsRepository {
    override fun setAppTheme(appThemeColors: PassworxColors) {
        preferences.edit().putString(APP_THEME_KEY, appThemeColors.name).apply()
    }

    override fun getAppTheme(supportsDynamic: Boolean): String {
        return preferences.getString(
            APP_THEME_KEY,
            if (supportsDynamic) PassworxColors.Dynamic.name else PassworxColors.Red.toString()
        ).toString()
    }
}