package com.gvelesiani.passmanager.data.database

import com.gvelesiani.passmanager.data.models.PasswordModel

interface LocalDataProvider {
    fun addNewPassword(pass: PasswordModel)
}