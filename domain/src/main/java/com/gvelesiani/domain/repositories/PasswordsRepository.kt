package com.gvelesiani.domain.repositories

import com.gvelesiani.common.models.data.PasswordDto
import kotlinx.coroutines.flow.Flow

interface PasswordsRepository {
    fun updatePassword(pass: PasswordDto)
    fun addNewPassword(pass: PasswordDto)
    fun getPasswords(isInTrash: Boolean): Flow<List<PasswordDto>>
    fun updateFavoriteState(isFavorite: Boolean, id: Int)
    fun getFavoritePasswords(): Flow<List<PasswordDto>>
    fun updateItemTrashState(isInTrash: Boolean, id: Int)
    fun deletePassword(passwordId: Int)
    fun searchPasswords(query: String, isInTrash: Boolean): Flow<List<PasswordDto>>
}