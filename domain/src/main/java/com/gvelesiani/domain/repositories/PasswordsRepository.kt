package com.gvelesiani.domain.repositories

import com.gvelesiani.domain.model.PasswordModel

interface PasswordsRepository {
    fun updatePassword(pass: PasswordModel)
    fun addNewPassword(pass: PasswordModel)
    fun getPasswords(isInTrash: Boolean): List<PasswordModel>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoritePasswords(): List<PasswordModel>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordModel>
}