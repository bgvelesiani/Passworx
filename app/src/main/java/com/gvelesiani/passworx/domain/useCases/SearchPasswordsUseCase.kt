package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.transformers.transformToModel

class SearchPasswordsUseCase(private val repository: Repository) :
    BaseUseCase<Pair<String, Boolean>, List<PasswordModel>>() {
    override suspend fun invoke(params: Pair<String, Boolean>): List<PasswordModel> {
        return repository.searchPasswords(query = params.first, isInTrash = params.second).map {
            it.transformToModel()
        }
    }
}