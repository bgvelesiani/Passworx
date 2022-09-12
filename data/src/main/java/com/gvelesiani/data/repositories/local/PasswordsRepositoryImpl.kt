package com.gvelesiani.data.repositories.local

import com.gvelesiani.data.database.database.PasswordDatabase
import com.gvelesiani.data.transformers.transformToDto
import com.gvelesiani.data.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository
import com.gvelesiani.domain.model.PasswordModel

class PasswordsRepositoryImpl constructor(
    private val database: PasswordDatabase
) : PasswordsRepository {
    override fun addNewPassword(pass: PasswordModel) {
        database.getPasswordDao.addNewPassword(pass.transformToDto())
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordModel> {
        return database.getPasswordDao.getPasswords(isInTrash).map {
            it.transformToModel()
        }
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
        database.getPasswordDao.updateFavoriteState(isFavorite, id)
    }

    override fun getFavoritePasswords(): List<PasswordModel> {
        return database.getPasswordDao.getFavorites().map {
            it.transformToModel()
        }
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {
        database.getPasswordDao.updateItemTrashState(isInTrash, id)
    }

    override fun deletePassword(passwordId: Int) {
        database.getPasswordDao.deletePassword(passwordId)
    }

    override fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordModel> {
        return database.getPasswordDao.searchPasswords(query, isInTrash).map {
            it.transformToModel()
        }
    }

    override fun updatePassword(pass: PasswordModel) {
        database.getPasswordDao.updatePassword(pass.transformToDto())
    }
}