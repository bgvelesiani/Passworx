package com.gvelesiani.passworx.data.transformers

import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.domain.model.PasswordModel

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