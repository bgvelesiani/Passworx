package com.gvelesiani.passworx.data.providers.local

import android.content.SharedPreferences
import com.gvelesiani.passworx.constants.MASTER_PASSWORD
import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.data.providers.local.database.PasswordDatabase

class LocalDataProviderImpl constructor(
    private val database: PasswordDatabase,
    private val preferences: SharedPreferences
) : LocalDataProvider {
    override fun addNewPassword(pass: PasswordDto) {
        database.getPasswordDao.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean,isInFavourites: Boolean): List<PasswordDto> {
        return database.getPasswordDao.getPasswords(isInTrash, isInFavourites)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        database.getPasswordDao.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoriteItems(isFavorite: Boolean): List<PasswordDto> {
        return database.getPasswordDao.getFavorites(isFavorite)
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
}