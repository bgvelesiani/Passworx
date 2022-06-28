package com.gvelesiani.passworx.data.repository

import com.gvelesiani.passworx.data.database.LocalDataProvider
import com.gvelesiani.passworx.data.models.PasswordModel

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override fun addNewPassword(pass: PasswordModel) {
        localDataProvider.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordModel> {
        return localDataProvider.getPasswords(isInTrash)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        localDataProvider.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoriteItems(isFavorite: Boolean): List<PasswordModel> {
        return localDataProvider.getFavoriteItems(isFavorite)
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        localDataProvider.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        localDataProvider.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordModel> {
        return localDataProvider.searchPasswords(query, isInTrash)
    }

    override fun createOrChangeMasterPassword(masterPassword: String) {
        localDataProvider.createOrChangeMasterPassword(masterPassword)
    }

    override fun getMasterPassword(): String {
        return localDataProvider.getMasterPassword()
    }
}