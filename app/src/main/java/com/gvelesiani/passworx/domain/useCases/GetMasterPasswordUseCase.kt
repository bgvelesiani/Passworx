package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class GetMasterPasswordUseCase(private val repository: Repository) : BaseUseCase<Unit, String>() {
    override suspend fun invoke(params: Unit): String {
        return repository.getMasterPassword()
    }
}