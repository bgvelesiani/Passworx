package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.transformers.transformToModel

class GetPasswordsUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params).map {
            it.transformToModel()
        }
    }
}