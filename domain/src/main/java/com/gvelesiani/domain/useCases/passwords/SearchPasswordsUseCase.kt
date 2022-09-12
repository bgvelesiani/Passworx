package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class SearchPasswordsUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<Pair<String, Boolean>, List<PasswordModel>>() {
    override suspend fun invoke(params: Pair<String, Boolean>): List<PasswordModel> {
        return repository.searchPasswords(query = params.first, isInTrash = params.second)
    }
}