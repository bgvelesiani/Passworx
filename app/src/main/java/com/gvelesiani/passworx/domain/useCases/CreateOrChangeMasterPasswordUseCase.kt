package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class CreateOrChangeMasterPasswordUseCase(private val repository: Repository) :
    BaseUseCase<String, Unit>() {
    override suspend operator fun invoke(params: String) {
        repository.createOrChangeMasterPassword(params)
    }
}