package com.gvelesiani.domain.useCases.appColors

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.AppColorsRepository

class GetAppColorsUseCase(val repository: AppColorsRepository): BaseUseCase<Boolean, String>() {
    override suspend fun invoke(params: Boolean): String {
        return repository.getAppTheme(params)
    }
}