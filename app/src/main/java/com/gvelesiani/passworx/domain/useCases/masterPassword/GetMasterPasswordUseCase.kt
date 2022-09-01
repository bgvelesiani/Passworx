package com.gvelesiani.passworx.domain.useCases.masterPassword

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.MasterPasswordRepository

class GetMasterPasswordUseCase(private val repository: MasterPasswordRepository) :
    BaseUseCase<Unit, String>() {
    override suspend fun invoke(params: Unit): String {
        return repository.getMasterPassword()
    }
}