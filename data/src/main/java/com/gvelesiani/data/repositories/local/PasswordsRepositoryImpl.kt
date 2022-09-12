package com.gvelesiani.data.repositories.local

import com.gvelesiani.common.models.data.PasswordDto
import com.gvelesiani.data.database.database.PasswordDatabase
import com.gvelesiani.domain.repositories.PasswordsRepository

class PasswordsRepositoryImpl constructor(
    private val database: PasswordDatabase
) : PasswordsRepository {
    override fun addNewPassword(pass: PasswordDto) {
        database.getPasswordDao.addNewPassword(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordDto> {
        return database.getPasswordDao.getPasswords(isInTrash)
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        database.getPasswordDao.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoritePasswords(): List<PasswordDto> {
        return database.getPasswordDao.getFavorites()
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        database.getPasswordDao.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        database.getPasswordDao.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto> {
        return database.getPasswordDao.searchPasswords(query, isInTrash)
    }

    override fun updatePassword(pass: PasswordDto) {
        database.getPasswordDao.updatePassword(pass)
    }
}