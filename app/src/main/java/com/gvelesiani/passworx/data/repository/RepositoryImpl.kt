package com.gvelesiani.passworx.data.repository

import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.data.providers.local.LocalDataProvider

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override fun addNewPassword(pass: PasswordDto) {
        localDataProvider.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean, isInFavourites: Boolean): List<PasswordDto> {
        return localDataProvider.getPasswords(isInTrash, isInFavourites)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        localDataProvider.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoriteItems(isFavorite: Boolean): List<PasswordDto> {
        return localDataProvider.getFavoriteItems(isFavorite)
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
}