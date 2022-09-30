package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoritePasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Unit, Flow<List<PasswordModel>>>() {
    override suspend fun invoke(params: Unit): Flow<List<PasswordModel>> {
        return repository.getFavoritePasswords().map { list ->
            list.map {
                it.transformToModel()
            }
        }
    }
}