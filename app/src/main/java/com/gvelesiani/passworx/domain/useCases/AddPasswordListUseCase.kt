package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.transformers.transformToDto

class AddPasswordListUseCase(val repository: Repository): BaseUseCase<List<PasswordModel>, Unit>() {
    override suspend fun invoke(params: List<PasswordModel>) {
        repository.addPasswordList(params.map { it.transformToDto() })
    }
}
