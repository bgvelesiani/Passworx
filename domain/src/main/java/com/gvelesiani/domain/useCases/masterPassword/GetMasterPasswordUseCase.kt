package com.gvelesiani.domain.useCases.masterPassword

import com.gvelesiani.domain.repositories.MasterPasswordRepository

class GetMasterPasswordUseCase(private val repository: MasterPasswordRepository) :
    com.gvelesiani.base.BaseUseCase<Unit, String>() {
    override suspend fun invoke(params: Unit): String {
        return repository.getMasterPassword()
    }
}