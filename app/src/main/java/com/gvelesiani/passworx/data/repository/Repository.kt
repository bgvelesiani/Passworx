package com.gvelesiani.passworx.data.repository

import com.gvelesiani.passworx.data.models.PasswordModel

interface Repository {
    fun addNewPassword(pass: PasswordModel)
    fun getPasswords(isInTrash: Boolean): List<PasswordModel>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoriteItems(isFavorite: Boolean): List<PasswordModel>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
}