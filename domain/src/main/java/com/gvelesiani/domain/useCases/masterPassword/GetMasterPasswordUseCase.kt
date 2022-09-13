package com.gvelesiani.domain.useCases.masterPassword

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.MasterPasswordRepository

class GetMasterPasswordUseCase(private val repository: MasterPasswordRepository) :
    BaseUseCase<Unit, String>() {
    override suspend fun invoke(params: Unit): String {
        return repository.getMasterPassword()
    }
}