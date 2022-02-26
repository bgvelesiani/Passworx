package com.gvelesiani.passmanager.domain.useCases

import com.gvelesiani.passmanager.base.BaseUseCase
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.data.repository.Repository

class GetPasswordsUseCase(private val repository: Repository): BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun run(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params)
    }
}