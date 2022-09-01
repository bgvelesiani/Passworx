package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.transformers.transformToDto

class UpdatePasswordUseCase(private val repository: Repository) : BaseUseCase<PasswordModel, Unit>() {
    override suspend fun invoke(params: PasswordModel) {
        repository.updatePassword(params.transformToDto())
    }
}