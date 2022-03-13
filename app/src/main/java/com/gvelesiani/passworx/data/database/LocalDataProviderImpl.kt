package com.gvelesiani.passworx.data.database

import android.content.SharedPreferences
import com.gvelesiani.passworx.data.database.database.PasswordDatabase
import com.gvelesiani.passworx.data.models.PasswordModel

class LocalDataProviderImpl constructor(
    private val database: PasswordDatabase,
    private val preferences: SharedPreferences
) : LocalDataProvider {
    override fun addNewPassword(pass: PasswordModel) {
        database.getPasswordDao.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordModel> {
        return database.getPasswordDao.getPasswords(isInTrash)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        database.getPasswordDao.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoriteItems(isFavorite: Boolean): List<PasswordModel> {
        return database.getPasswordDao.getFavorites(isFavorite)
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        database.getPasswordDao.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        database.getPasswordDao.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String): List<PasswordModel> {
        return database.getPasswordDao.searchPasswords(query)
    }

    override fun saveMasterPassword(masterPassword: String) {
        preferences.edit().putString("MASTER_PASSWORD", masterPassword).apply()
    }


    override fun getMasterPassword(): String {
        return preferences.getString("MASTER_PASSWORD", "").toString()
    }
}