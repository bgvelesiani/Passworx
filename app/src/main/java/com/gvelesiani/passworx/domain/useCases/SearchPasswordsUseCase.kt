package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.data.repository.Repository

class SearchPasswordsUseCase(private val repository: Repository) :
    BaseUseCase<Pair<String, Boolean>, List<PasswordModel>>() {
    override suspend operator fun invoke(params: Pair<String, Boolean>): List<PasswordModel> {
        return repository.searchPasswords(query = params.first, isInTrash = params.second)
    }
}