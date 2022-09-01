package com.gvelesiani.passworx.domain.repositories.local

import com.gvelesiani.passworx.data.dto.PasswordDto

interface PasswordsRepository {
    fun updatePassword(pass: PasswordDto)
    fun addNewPassword(pass: PasswordDto)
    fun getPasswords(isInTrash: Boolean): List<PasswordDto>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoritePasswords(): List<PasswordDto>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto>
}