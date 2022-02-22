package com.gvelesiani.passmanager.data.database

import com.gvelesiani.passmanager.data.database.database.PasswordDatabase
import com.gvelesiani.passmanager.data.models.PasswordModel

class LocalDataProviderImpl constructor(
    private val database: PasswordDatabase
) : LocalDataProvider {
    override fun addNewPassword(pass: PasswordModel) {
        database.getPasswordDao.addNewPassword(pass)
    }
}