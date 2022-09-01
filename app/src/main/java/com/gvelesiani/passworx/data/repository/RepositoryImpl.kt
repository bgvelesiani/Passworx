package com.gvelesiani.passworx.data.repository

import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.data.providers.local.LocalDataProvider

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override fun addNewPassword(pass: PasswordDto) {
        localDataProvider.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordDto> {
        return localDataProvider.getPasswords(isInTrash)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        localDataProvider.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoritePasswords(): List<PasswordDto> {
        return localDataProvider.getFavoritePasswords()
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        localDataProvider.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        localDataProvider.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto> {
        return localDataProvider.searchPasswords(query, isInTrash)
    }

    override fun createOrChangeMasterPassword(masterPassword: String) {
        localDataProvider.createOrChangeMasterPassword(masterPassword)
    }

    override fun getMasterPassword(): String {
        return localDataProvider.getMasterPassword()
    }

    override fun preventTakingScreenshots(prevent: Boolean) {
        localDataProvider.preventTakingScreenshots(prevent)
    }

    override fun getTakingScreenshotsStatus(): Boolean {
        return localDataProvider.getTakingScreenshotsStatus()
    }

    override fun allowBiometrics(allow: Boolean) {
        localDataProvider.allowBiometrics(allow)
    }

    override fun getBiometricsAllowingStatus(): Boolean {
        return localDataProvider.getBiometricsAllowingStatus()
    }

    override fun finishIntro() {
        localDataProvider.finishIntro()
    }

    override fun isIntroFinished(): Boolean {
        return localDataProvider.isIntroFinished()
    }

    override fun updatePassword(pass: PasswordDto) {
        localDataProvider.updatePassword(pass)
    }

    override fun savePasswordsAsString(passwords: String) {
        localDataProvider.savePasswordsAsString(passwords)
    }

    override fun getPasswordsAsString(): String {
        return localDataProvider.getPasswordsAsString()
    }
}