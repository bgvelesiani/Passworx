package com.gvelesiani.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.data.common.IS_INTRO_FINISHED
import com.gvelesiani.domain.repositories.IntroRepository

class IntroRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : IntroRepository {
    override fun finishIntro() {
        preferences.edit().putBoolean(IS_INTRO_FINISHED, true).apply()
    }

    override fun isIntroFinished(): Boolean {
        return preferences.getBoolean(IS_INTRO_FINISHED, false)
    }
}