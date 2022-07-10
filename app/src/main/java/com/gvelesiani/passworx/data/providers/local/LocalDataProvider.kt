package com.gvelesiani.passworx.data.providers.local

import com.gvelesiani.passworx.data.dto.PasswordDto

interface LocalDataProvider {
    fun addNewPassword(pass: PasswordDto)
    fun getPasswords(isInTrash: Boolean, isInFavourites: Boolean): List<PasswordDto>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoriteItems(isFavorite: Boolean): List<PasswordDto>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto>
    fun createOrChangeMasterPassword(masterPassword: String)
    fun getMasterPassword(): String
}