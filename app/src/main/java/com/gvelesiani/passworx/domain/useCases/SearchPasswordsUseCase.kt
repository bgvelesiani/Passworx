package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.data.repository.Repository

class SearchPasswordsUseCase(private val repository: Repository) :
    BaseUseCase<String, List<PasswordModel>>() {
    override suspend fun run(params: String): List<PasswordModel> {
        return repository.searchPasswords(params)
    }
}