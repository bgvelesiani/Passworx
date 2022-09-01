package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.transformers.transformToModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class SearchPasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Pair<String, Boolean>, List<PasswordModel>>() {
    override suspend fun invoke(params: Pair<String, Boolean>): List<PasswordModel> {
        return repository.searchPasswords(query = params.first, isInTrash = params.second).map {
            it.transformToModel()
        }
    }
}