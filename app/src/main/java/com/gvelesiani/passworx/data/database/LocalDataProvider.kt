package com.gvelesiani.passworx.data.database

import com.gvelesiani.passworx.data.models.PasswordModel

interface LocalDataProvider {
    fun addNewPassword(pass: PasswordModel)
    fun getPasswords(isInTrash: Boolean): List<PasswordModel>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoriteItems(isFavorite: Boolean): List<PasswordModel>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String): List<PasswordModel>
    fun createOrChangeMasterPassword(masterPassword: String)
    fun getMasterPassword(): String
}