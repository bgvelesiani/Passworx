package com.gvelesiani.passmanager.data.repository

import com.gvelesiani.passmanager.data.models.PasswordModel

interface Repository {
    fun addNewPassword(pass: PasswordModel)
    fun getPasswords(): List<PasswordModel>
}