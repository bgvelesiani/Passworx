package com.gvelesiani.passworx.fakes

import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.data.repository.Repository

class FakeRepository(private val passwords: MutableList<PasswordModel>) : Repository {
    override fun addNewPassword(pass: PasswordModel) {
        passwords.add(pass)
    }

    override fun getPasswords(isInTrash: Boolean): List<PasswordModel> {
        return passwords.filter {
            it.isInTrash == isInTrash
        }
    }

    override fun updateFavoriteState(isFavorite: Boolean, id: Int) {
    }

    override fun getFavoriteItems(isFavorite: Boolean): List<PasswordModel> {
        return passwords.filter {
            it.isFavorite == isFavorite
        }
    }

    override fun updateItemTrashState(isInTrash: Boolean, id: Int) {

    }

    override fun deletePassword(passwordId: Int) {
        passwords.remove(passwords.find { it.passwordId == passwordId })
    }

    override fun searchPasswords(query: String): List<PasswordModel> {
        return emptyList()
    }

    override fun createOrChangeMasterPassword(masterPassword: String) {
    }

    override fun getMasterPassword(): String {
        return ""
    }
}