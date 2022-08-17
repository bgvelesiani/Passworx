package com.gvelesiani.passworx.data.providers.local

import android.content.SharedPreferences
import androidx.sqlite.db.SimpleSQLiteQuery
import com.gvelesiani.passworx.constants.BIOMETRICS_ALLOWED
import com.gvelesiani.passworx.constants.IS_INTRO_FINISHED
import com.gvelesiani.passworx.constants.MASTER_PASSWORD
import com.gvelesiani.passworx.constants.PREVENT_TAKING_SCREENSHOTS
import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.data.providers.local.database.PasswordDatabase

class LocalDataProviderImpl constructor(
    private val database: PasswordDatabase,
    private val preferences: SharedPreferences
) : LocalDataProvider {
    override fun addNewPassword(pass: PasswordDto) {
        database.getPasswordDao.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordDto> {
        return database.getPasswordDao.getPasswords(isInTrash)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        database.getPasswordDao.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoritePasswords(): List<PasswordDto> {
        return database.getPasswordDao.getFavorites()
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        database.getPasswordDao.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        database.getPasswordDao.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto> {
        return database.getPasswordDao.searchPasswords(query, isInTrash)
    }

    override fun createOrChangeMasterPassword(masterPassword: String) {
        preferences.edit().putString(MASTER_PASSWORD, masterPassword).apply()
    }

    override fun getMasterPassword(): String {
        return preferences.getString(MASTER_PASSWORD, "").toString()
    }

    override fun preventTakingScreenshots(prevent: Boolean) {
        preferences.edit().putBoolean(PREVENT_TAKING_SCREENSHOTS, prevent).apply()
    }

    override fun getTakingScreenshotsStatus(): Boolean {
        return preferences.getBoolean(PREVENT_TAKING_SCREENSHOTS, false)
    }

    override fun allowBiometrics(allow: Boolean) {
        preferences.edit().putBoolean(BIOMETRICS_ALLOWED, allow).apply()
    }

    override fun getBiometricsAllowingStatus(): Boolean {
        return preferences.getBoolean(BIOMETRICS_ALLOWED, false)
    }

    override fun finishIntro() {
        preferences.edit().putBoolean(IS_INTRO_FINISHED, true).apply()
    }

    override fun isIntroFinished(): Boolean {
        return preferences.getBoolean(IS_INTRO_FINISHED, false)
    }

    override fun checkPoint(): Int {
        return database.getPasswordDao.checkpoint((SimpleSQLiteQuery("pragma wal_checkpoint(full)")))
    }

    override fun addPasswordList(list: List<PasswordDto>) {
        database.getPasswordDao.addPasswordList(list)
    }
}