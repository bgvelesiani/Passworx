package com.gvelesiani.common.transformers

import com.gvelesiani.common.models.data.PasswordDto
import com.gvelesiani.common.models.domain.PasswordModel

fun PasswordModel.transformToDto(): PasswordDto = PasswordDto(
    this.passwordId,
    this.password,
    this.passwordTitle,
    this.websiteOrAppName,
    this.emailOrUserName,
    this.label,
    this.isFavorite,
    this.isInTrash
)

fun PasswordDto.transformToModel(): PasswordModel = PasswordModel(
    this.passwordId,
    this.password,
    this.passwordTitle,
    this.websiteOrAppName,
    this.emailOrUserName,
    this.label,
    this.isFavorite,
    this.isInTrash
)