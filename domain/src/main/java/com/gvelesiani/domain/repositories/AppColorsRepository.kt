package com.gvelesiani.domain.repositories

import com.gvelesiani.common.models.PassworxColors

interface AppColorsRepository {
    fun setAppTheme(appThemeColors: PassworxColors)
    fun getAppTheme(supportsDynamic: Boolean): String
}