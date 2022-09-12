package com.gvelesiani.domain.useCases.masterPassword

import com.gvelesiani.domain.repositories.MasterPasswordRepository

class CreateOrChangeMasterPasswordUseCase(private val repository: MasterPasswordRepository) :
    com.gvelesiani.base.BaseUseCase<String, Unit>() {
    override suspend fun invoke(params: String) {
        repository.createOrChangeMasterPassword(params)
    }
}