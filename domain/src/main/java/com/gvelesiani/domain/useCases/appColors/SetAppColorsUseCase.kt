package com.gvelesiani.domain.useCases.appColors

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.domain.repositories.AppColorsRepository

class SetAppColorsUseCase(val repository: AppColorsRepository) :
    BaseUseCase<PassworxColors, Unit>() {
    override suspend fun invoke(params: PassworxColors) {
        repository.setAppTheme(params)
    }
}