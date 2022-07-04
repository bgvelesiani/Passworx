package com.gvelesiani.passworx.data.repository

import com.gvelesiani.passworx.data.dto.PasswordDto

interface Repository {
    fun addNewPassword(pass: PasswordDto)
    fun getPasswords(isInTrash: Boolean): List<PasswordDto>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoriteItems(isFavorite: Boolean): List<PasswordDto>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto>
    fun createOrChangeMasterPassword(masterPassword: String)
    fun getMasterPassword(): String
}