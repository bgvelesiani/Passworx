package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchPasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Pair<String, Boolean>, Flow<List<PasswordModel>>>() {
    override suspend fun invoke(params: Pair<String, Boolean>): Flow<List<PasswordModel>> {
        return repository.searchPasswords(query = params.first, isInTrash = params.second).map { list ->
            list.map { it.transformToModel() }
        }
    }
}