package com.gvelesiani.passworx.domain.useCases.masterPassword

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.MasterPasswordRepository

class CreateOrChangeMasterPasswordUseCase(private val repository: MasterPasswordRepository) :
    BaseUseCase<String, Unit>() {
    override suspend fun invoke(params: String) {
        repository.createOrChangeMasterPassword(params)
    }
}