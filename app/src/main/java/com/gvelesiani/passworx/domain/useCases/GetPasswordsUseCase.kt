package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.data.repository.Repository

class GetPasswordsUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params)
    }
}