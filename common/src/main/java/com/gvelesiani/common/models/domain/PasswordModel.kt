package com.gvelesiani.common.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PasswordModel(
    val passwordId: Int = 0,
    val password: String = "",
    val passwordTitle: String = "",
    val websiteOrAppName: String = "",
    val emailOrUserName: String = "",
    val label: String = "",
    val isFavorite: Boolean = false,
    val isInTrash: Boolean = false
) : Parcelable