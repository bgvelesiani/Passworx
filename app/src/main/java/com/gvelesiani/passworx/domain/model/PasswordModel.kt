package com.gvelesiani.passworx.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordModel(
    val passwordId: Int = 0,
    val password: String = "",
    val passwordTitle: String = "",
    val websiteOrAppName: String = "",
    val emailOrUserName: String = "",
    val label: String = "",
    val isFavorite: Boolean = false,
    val isInTrash: Boolean = false
) : Parcelable