package com.gvelesiani.passmanager.data.repository

import com.gvelesiani.passmanager.data.database.LocalDataProvider
import com.gvelesiani.passmanager.data.models.PasswordModel

class RepositoryImpl(private val localDataProvider: LocalDataProvider) : Repository {
    override fun addNewPassword(pass: PasswordModel) {
        localDataProvider.addNewPassword(pass)
    }

    override fun getPasswords(): List<PasswordModel> {
        return localDataProvider.getPasswords()
    }

}